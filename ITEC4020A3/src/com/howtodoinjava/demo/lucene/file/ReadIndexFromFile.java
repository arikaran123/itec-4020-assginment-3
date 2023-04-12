package com.howtodoinjava.demo.lucene.file;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ReadIndexFromFile {
    private static final String INDEX_DIR = "indexedFiles";

    public static void main(String[] args) throws Exception {
        IndexSearcher searcher = createSearcher();

        List<SearchResult> results = new ArrayList<>();
        searchInContent("RemoteSensing", searcher, results);

        // Sort the results by decreasing order of relevance
        Collections.sort(results, Collections.reverseOrder());

        // Print the ranking results
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            System.out.println("401 Q0 " + result.getId() + " " + (i + 1) + " " + result.getScore() + " Group8");
        }
    }

    private static void searchInContent(String textToFind, IndexSearcher searcher, List<SearchResult> results)
            throws Exception {
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);

        TopDocs hits = searcher.search(query, 20);

        for (ScoreDoc sd : hits.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            results.add(new SearchResult(d.get("id"), sd.score));
        }
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    private static class SearchResult implements Comparable<SearchResult> {
        private final String id;
        private final float score;

        public SearchResult(String id, float score) {
            this.id = id;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public float getScore() {
            return score;
        }

        @Override
        public int compareTo(SearchResult o) {
            return Float.compare(score, o.score);
        }
    }
}
