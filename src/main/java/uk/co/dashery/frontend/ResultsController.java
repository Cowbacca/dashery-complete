package uk.co.dashery.frontend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
class ResultsController {

    private final String appId;
    private final String apiKey;
    private final String indexName;

    @Inject
    ResultsController(@Value("${algoliasearch.application.id}") String appId, @Value("${algoliasearch.api.key.search}") String apiKey, @Value("${indexer.index.name}") String indexName) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.indexName = indexName;
    }

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping("/results")
    String results(Model model) {
        model.addAttribute("appId", appId);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("indexName", indexName);
        return "results";
    }
}
