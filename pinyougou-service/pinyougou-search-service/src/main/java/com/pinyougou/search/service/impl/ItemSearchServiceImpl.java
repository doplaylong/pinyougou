package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜索服务接口实现类
 *
 * @author 27847
 * @version 1.0
 * @date 2019/03/13 16:03
 **/

@Service(interfaceName = "com.pinyougou.service.ItemSearchService")
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> pamars) {
        /*创建Map集合封装返回数据*/
        Map<String, Object> data = new HashMap<>();
        /*获取检索关键字*/
        String keywords = (String) pamars.get("keywords");
        /*获取当前页码*/
        Integer page = (Integer) pamars.get("page");
        if (page == null || page < 1) {
            /*默认第一页*/
            page = 1;
        }
        /*获取每页显示的记录数*/
        Integer rows = (Integer) pamars.get("rows");
        if (rows == null || page < 1) {
            /*默认20条记录*/
            rows = 20;
        }
        /*判断检索关键字是否为空*/
        if (StringUtils.isNoneBlank(keywords)) { // 高亮查询
            /*创建高亮查询对象*/
            HighlightQuery highlightQuery = new SimpleHighlightQuery();
            /*创建高亮选项对象*/
            HighlightOptions highlightOptions = new HighlightOptions();
            /*设置高亮需要的参数*/
            highlightOptions.setSimplePrefix("<font color='red'>");
            highlightOptions.setSimplePostfix("</font>");
            highlightOptions.addField("title");
            /*高亮查询对象添加高亮选项对象*/
            highlightQuery.setHighlightOptions(highlightOptions);
            /*创建查询条件对象*/
            Criteria criteria = new Criteria("keywords").is(keywords);
            /*添加查询条件*/
            highlightQuery.addCriteria(criteria);
            // 按照商品分类过滤
            if (!"".equals(pamars.get("category"))) {
                Criteria criteria1 = new Criteria("category").is(pamars.get("category"));
                // 添加过滤条件
                highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            // 按品牌过滤
            if (!"".equals(pamars.get("brand"))) {
                Criteria criteria1 = new Criteria("brand").is(pamars.get("brand"));
                // 添加过滤条件
                highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            // 按规格过滤
            if (pamars.get("spec") != null) {
                // 将json对象转换为Map类型
                Map<String,String> specMap = (Map)pamars.get("spec");
                for (String key : specMap.keySet()) {
                    Criteria criteria1 = new Criteria("spec_" + key).is(specMap.get(key));
                    // 添加过滤条件
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
            }
            // 按价格过滤
            if (!"".equals(pamars.get("price"))) {
                // 得到价格范围数组
                String[] prices = pamars.get("price").toString().split("-");
                System.out.println("price:" + pamars.get("price"));
                // 如果价格区间起点不等于0
                if (!prices[0].equals("0")) {
                    Criteria criteria1 = new Criteria("price").greaterThanEqual(prices[0]);
                    // 添加过滤条件
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
                // 如果价格区间终点不等于星号
                if (!prices[1].equals("*")) {
                    Criteria criteria1 = new Criteria("price").lessThanEqual(prices[1]);
                    // 添加过滤条件
                    highlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
            }
            // 添加排序
            String sortValue = (String) pamars.get("sort"); // ASC DESC
            String sortField = (String) pamars.get("sortField"); // 排序域名称
            if (StringUtils.isNoneBlank(sortValue) && StringUtils.isNoneBlank(sortField)) {
                Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ?
                        Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                // 增加排序条件到高亮查询对象
                highlightQuery.addSort(sort);
            }
            /*设置起始记录查询数*/
            highlightQuery.setOffset((page -1) * rows);
            /*设置每页显示记录数*/
            highlightQuery.setRows(rows);

            /*得到高亮分页查询对象*/
            HighlightPage<SolrItem> highlightPage = solrTemplate.queryForHighlightPage(highlightQuery, SolrItem.class);
            /*循环高亮项集合*/
            for (HighlightEntry<SolrItem> highlightEntry : highlightPage.getHighlighted()) {
                /*获取检索到的原实体*/
                SolrItem solrItem = highlightEntry.getEntity();
                /*判断高亮集合及集合中第一个Field的高亮内容*/
                if (highlightEntry.getHighlights().size() > 0
                        && highlightEntry.getHighlights().get(0)
                        .getSnipplets().size() > 0) {
                    /*设置高亮的结果*/
                    solrItem.setTitle(highlightEntry.getHighlights().get(0).getSnipplets().get(0));
                }
            }
            data.put("rows", highlightPage.getContent());
            // 设置总页数
            data.put("totalPages", highlightPage.getTotalPages());
            // 设置总记录数
            data.put("total", highlightPage.getTotalElements());
        } else {
            Query query = new SimpleQuery("*:*");
            // 设置起始记录查询数
            query.setOffset((page - 1) * rows);
            // 设置每页显示记录数
            query.setRows(rows);
            ScoredPage<SolrItem> scoredPage = solrTemplate.queryForPage(query, SolrItem.class);
            /*获取内容*/
            data.put("rows", scoredPage.getContent());
            // 设置总页数
            data.put("totalPages", scoredPage.getTotalPages());
            // 设置总记录数
            data.put("total", scoredPage.getTotalElements());
        }
        return data;
    }
}
