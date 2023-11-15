package io.collective.articles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.collective.restsupport.BasicHandler;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ArticlesController extends BasicHandler {
    private final ArticleDataGateway gateway;

    public ArticlesController(ObjectMapper mapper, ArticleDataGateway gateway) {
        super(mapper);
        this.gateway = gateway;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        get("/articles", List.of("application/json", "text/html"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *all* articles, map record to infos, and send back a collection of article infos
                Collection<ArticleRecord> allArticles = gateway.findAll();

                writeJsonBody(servletResponse,
                        allArticles.stream()
                        .map(article -> new ArticleInfo(article.getId(), article.getTitle()))
                        .toList());
            }
        });

        get("/available", List.of("application/json"), request, servletResponse, () -> {

            { // todo - query the articles gateway for *available* articles, map records to infos, and send back a collection of article infos
                Collection<ArticleRecord> allAvailableArticles = gateway.findAll();
                writeJsonBody(servletResponse, allAvailableArticles);
                System.out.println("HIIIIIIIIIIIIII");
                System.out.println(Arrays.toString(allAvailableArticles.toArray()));
                allAvailableArticles.stream()
                        .map(article -> new ArticleInfo(article.getId(), article.getTitle()))
                        .toList();
            }
        });
    }
}
