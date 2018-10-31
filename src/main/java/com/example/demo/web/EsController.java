package com.example.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.example.demo.dal.entity.main.es.Test2;
import com.example.demo.dal.entity.main.es.VO;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.*;

import static org.elasticsearch.client.Requests.searchRequest;

@Controller
public class EsController {

    /**
     * 通过这个可以获取参数
     */
    @Autowired
    private Environment env;

    /**
     * elasticsearch
     */
    @Autowired
    private RestHighLevelClient restHighLevelClient;


//=======================================indexAPI=========================================================

    /**
     * 整合es--插入索引
     */
    @RequestMapping("/es-index")
    public void testEsIndex() throws IOException {

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(jsonMap);

        ActionListener<IndexResponse> indexResponseActionListener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {

                System.out.println("=================================索引插入成功了====================================");
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引插入失败了====================================");
            }
        };

        restHighLevelClient.indexAsync(indexRequest, indexResponseActionListener);
    }

    /**
     * 整合es--查看索引
     */
    @RequestMapping("/es-get")
    public void esGet() {

        GetRequest getRequest = new GetRequest(
                "test11",
                "doc",
                "1");

        ActionListener<GetResponse> getResponseActionListener = new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse getResponse) {
                System.out.println("=================================索引获取成功了====================================");
                System.out.println(getResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引获取失败了====================================");
            }
        };

        restHighLevelClient.getAsync(getRequest, getResponseActionListener);
    }

    /**
     * 整合es--删除索引
     */
    @RequestMapping("/es-del")
    public void esDelete() {

        DeleteRequest deleteRequest = new DeleteRequest(
                "posts",
                "doc",
                "1");

        ActionListener<DeleteResponse> deleteResponseActionListener = new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {
                System.out.println("=================================索引删除成功了====================================");
                System.out.println(deleteResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引删除失败了====================================");
            }
        };

        restHighLevelClient.deleteAsync(deleteRequest, deleteResponseActionListener);

    }

    /**
     * 整合es--更新索引
     */
    @RequestMapping("/es-update")
    public void esUpdate() {

        //更改
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest updateRequest = new UpdateRequest("posts", "doc", "1")
                .doc(jsonMap);

        ActionListener<UpdateResponse> updateResponseActionListener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                System.out.println("=================================索引更新成功了====================================");
                System.out.println(updateResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("=================================索引更新失败了====================================");
            }
        };

        restHighLevelClient.updateAsync(updateRequest, updateResponseActionListener);
    }

    /**
     * 使用jdbc方式操作es
     *
     * @throws SQLException
     */
    @RequestMapping("/es-jdbc")
    public void test() throws SQLException {

        String driver = "org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver";

//        try {
////            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        String address = "jdbc:es://192.168.21.129:9200";
        Properties connectionProperties = new Properties();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(address, connectionProperties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement statement = connection.createStatement();
        ResultSet results = null;
        try {
            //因为没有白金许可  所以报错（后面再调吧，先使用Java High Level REST Client操作es）
            results = statement.executeQuery("SELECT * FROM bank  LIMIT 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (results.next()) {
            System.out.println(results.getString(1));
        }

        //关闭资源
        results.close();
        statement.close();
        connection.close();
    }


    //===========================================searchAPI==========================================

    /**
     * 整合es--简单查询
     */
    @RequestMapping("/es/search-simple")
    public void esSearchSimple() {

        //创建searchRequest和searchSourceBuilder
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置index
        searchRequest("book01");

        //全文查询自动匹配
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("西").analyzer("ik_max_word");

        searchSourceBuilder.query(queryStringQueryBuilder);
        //完全匹配查询
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 带分词查询模糊查询
//        searchSourceBuilder.query(QueryBuilders.matchQuery("*", "小子").boost(3.2F));
        // 不带分词模糊查询
//        searchSourceBuilder.query(QueryBuilders.termQuery("book_author", "小子"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("create_time").order(SortOrder.ASC));

        //过滤字段
//        String[] includeFields = new String[] {"book_name", "book_author","create_time"};
//        String[] excludeFields = new String[] {"book_id"};
//        searchSourceBuilder.fetchSource(includeFields, excludeFields);

        //TODO 聚合要研究下

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("book_author");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
        highlightBuilder.field(highlightUser);
        searchSourceBuilder.highlighter(highlightBuilder);

        //添加到源
        searchRequest.source(searchSourceBuilder);

        //执行
        ActionListener<SearchResponse> searchResponseActionListener = new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                System.out.println("查询返回结果" + searchResponse);

                //获取高亮结果
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits.getHits()) {
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField highlight = highlightFields.get("book_author");
                    Text[] fragments = highlight.fragments();
                    String fragmentString = fragments[0].string();
                }

            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("错误消息" + e);
            }
        };
        restHighLevelClient.searchAsync(searchRequest, searchResponseActionListener);
    }

    /**
     * 整合es--索引映射
     */
    @RequestMapping("/es/mapping-index")
    public void esMappingIndex() {

//        CreateIndexRequest request = new CreateIndexRequest("twitter");
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2)
//        );
//        request.mapping("tweet", "message", "type=text");
//
//        ActionListener<CreateIndexResponse> createIndexResponseActionListener =
//                new ActionListener<CreateIndexResponse>() {
//
//                    @Override
//                    public void onResponse(CreateIndexResponse createIndexResponse) {
//                        System.out.println("createIndexResponse");
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        System.out.println(e);
//                    }
//                };
//
//        restHighLevelClient.indices().createAsync(request, createIndexResponseActionListener);

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("twitter");
        try {
            DeleteIndexResponse deleteIndexResponse = restHighLevelClient.indices().delete(deleteIndexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 整合es--多词查询
     */
    @RequestMapping("/es/search-multi")
    public void esSearchMulti() {

        //创建searchRequest和searchSourceBuilder

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest();
        searchSourceBuilder.query(QueryBuilders.matchQuery("book_author", "小子").boost(1F));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);
        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("book_author", "罗贯中").boost(3F));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.sort(new FieldSortBuilder("create_time").order(SortOrder.ASC));
        try {
            MultiSearchResponse response = restHighLevelClient.multiSearch(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 整合es--watcher  6.3自带  只需要有一个license证书就好
     */
    @RequestMapping("/es/watcher")
    public void esWatcher() {
//        RestClient restClient = RestClient.builder(
//                new HttpHost("localhost", 9201, "http")).build();
//        WatcherClient watcherClient = new WatcherClient(restClient);
//        watcherClient
    }


    @RequestMapping("/es/aggregation")
    public void queryTest() {
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.from(0);
//        sourceBuilder.size(10);
////        sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "费德勒");
//        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("message", "this is a text");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("tag", "体育");
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
//        rangeQueryBuilder.gte("2018-01-26T08:00:00Z");
//        rangeQueryBuilder.lte("2018-01-26T20:00:00Z");
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
//        QueryBuilders.prefixQuery("value", "vi").queryName("user").boost(2.0F);
//        QueryBuilders.fuzzyQuery("QueryBuilders", "vi");
//
//
//        boolBuilder.must(matchQueryBuilder);
//        boolBuilder.must(termQueryBuilder);
//        boolBuilder.must(rangeQueryBuilder);
//
//        sourceBuilder.query(boolBuilder);
//        SearchRequest searchRequest = new SearchRequest("book01");
//        searchRequest.types("book01");
//        searchRequest.source(sourceBuilder);
//        try {
//            SearchResponse response = restHighLevelClient.search(searchRequest);
//            System.out.println(response);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("avg_grade").field("grade");


//        SearchResponse searchResponse = new SearchResponse();
//        Terms byCompanyAggregation = searchResponse.getAggregations().get("avg_grade");
//        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
//        Avg averageAge = elasticBucket.getAggregations( ).get("average_age");
//        double avg = averageAge.getValue();


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("company")
                .field("book01.book_id");
        aggregation.subAggregation(AggregationBuilders.avg("average_age")
                .field("book_id"));
        searchSourceBuilder.aggregation(aggregation);

        SearchResponse searchResponse = null;
        SearchRequest searchRequest = new SearchRequest("book01");
        searchRequest.types("book01");
        searchRequest.source(searchSourceBuilder);
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Aggregations aggregations = searchResponse.getAggregations();
        Terms byCompanyAggregation = aggregations.get("company");
        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
        Avg averageAge = elasticBucket.getAggregations().get("average_age");
        double avg = averageAge.getValue();
    }


    /**
     * 整合es--插入json测试
     */
    @RequestMapping("/es/json")
    public void esJsonTest() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());

            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(builder);


        //读取一个文件中的数据
        String filePath = "D:/20180909.txt";
        BulkRequest request = new BulkRequest();
        String jsonString = readFileToString(filePath);
        Map<String, Object> tests = null;

        List<Test2> tests1 = JSONObject.parseArray(jsonString.replace("ods_i_f_ldrk_ldrkxxb.", ""), Test2.class);

        long start = System.currentTimeMillis();
        int i = 0;
        for (Test2 str : tests1) {
            int a = i++;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("userName", str.getXm());
            jsonMap.put("address", str.getHjdxz());
            request.add(new IndexRequest("test15", "doc", String.valueOf(a))
                    .source(XContentType.JSON, "filed", jsonMap, "analyzer", "ik_max_word", "type", "text"));
        }
        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                    if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
                            || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
                        IndexResponse indexResponse = (IndexResponse) itemResponse;

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;

                    } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, listener);
        long end = System.currentTimeMillis();

        long sj = (end - start);
        System.out.println(sj);
    }

    /**
     * 整合es--测试自动补全
     */
    @RequestMapping("/es/suggestions")
    public void esSuggestions() {


        SearchRequest searchRequest = new SearchRequest("book01");

        //设置index
        searchRequest.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("book_name").text("西").analyzer("ik_max_word");

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);

        searchSourceBuilder.suggest(suggestBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Suggest suggest = searchResponse.getSuggest();
        TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
        for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
            for (TermSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
            }
        }

    }


    public static String readFileToString(String path) {
        // 定义返回结果
        String jsonString = "";

        BufferedReader in = null;
        try {
            // 读取文件
            in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            String thisLine;
            while ((thisLine = in.readLine()) != null) {
                jsonString += thisLine;
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException el) {
                }
            }
        }

        // 返回拼接好的JSON String
        return jsonString;
    }

    public static void main(String[] args) {
        // 读入上面输出的文件
        long start = System.currentTimeMillis();
        String filePath = "D:/201808090.txt";
        JSONReader reader = null;
        try {
            reader = new JSONReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader.startArray();
        while (reader.hasNext()) {
            String key = reader.readString();
//            VO vo = reader.readObject(VO. class);
            System.out.println(key);
            System.out.println("===========");
        }
        reader.endArray();
        reader.close();
        long end = System.currentTimeMillis();
        System.out.println("用时" + (end - start) + "ms");
    }


    /**
     * 整合es第一步  建立索引
     */
    @RequestMapping("/es/sadas")
    public void esSuggestions1() throws IOException {

        //建立索引
//        Map<String, Object> jsonMap = new HashMap<>();
//        IndexRequest indexRequest = new IndexRequest("news_website1", "news");
//        indexRequest.source(jsonMap);
//        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        //设置mapping
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject().
//                startObject("news").
//                startObject("properties").
//                startObject("title").field("type","text").field("analyzer", "ik_max_word").
//                startObject("fields").
//                startObject("suggest").field("type", "completion").field("analyzer","ik_max_word").
//                endObject().
//                endObject().
//                endObject().
//                startObject("content").field("type","text").field("analyzer","ik_max_word").
//                endObject().
//                endObject().
//                endObject().
//                endObject();
//
//        PutMappingRequest putMappingRequest = new PutMappingRequest("news_website1");
//        putMappingRequest.type("news");
//        putMappingRequest.source(builder);
//        PutMappingResponse putMappingResponse = restHighLevelClient.indices().putMapping(putMappingRequest,RequestOptions.DEFAULT);


        //插入数据
        Map<String, Object> jsonMap3 = new HashMap<>();
        jsonMap3.put("title", "大话西游电影");
        jsonMap3.put("content", "大话西游的电影时隔20年即将在2017年4月重映");
        Map<String, Object> jsonMap1 = new HashMap<>();
        jsonMap1.put("title", "大话西游手游");
        jsonMap1.put("content", "网易游戏近日出品了大话西游经典IP的手游，正在火爆内测中");
        Map<String, Object> jsonMap2 = new HashMap<>();
        jsonMap2.put("title", "大话西游小说");
        jsonMap2.put("content", "某知名网络小说作家已经完成了大话西游同名小说的出版");
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("news_website1", "news").source(jsonMap1));
        bulkRequest.add(new IndexRequest("news_website1", "news").source(jsonMap2));
        bulkRequest.add(new IndexRequest("news_website1", "news").source(jsonMap3));
        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

    }


    /**
     * 整合es--测试自动补全
     */
    @RequestMapping("/es/sss")
    public void esSuggestions2() throws IOException {

        SearchRequest searchRequest = new SearchRequest("news_website1");

        //设置index
        searchRequest.types("news");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion("title.filed.suggest").prefix("大话西游").analyzer("ik_max_word");

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_user", completionSuggestionBuilder);

        searchSourceBuilder.suggest(suggestBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Suggest suggest = searchResponse.getSuggest();
        CompletionSuggestion completionSuggestion = suggest.getSuggestion("suggest_user");
        for (CompletionSuggestion.Entry entry : completionSuggestion.getEntries()) {
            for (CompletionSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
            }
        }

    }

}


