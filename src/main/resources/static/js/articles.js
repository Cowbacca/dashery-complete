$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://dashery-complete-staging.herokuapp.com/blog/articles.xml",
        dataType: "xml",
        success: xmlParser
    });
});

function xmlParser(xml) {
    $(xml).find('entry:not(:has(category[term="development"]))').each(function () {
        var title = $(this).find('title').text();
        var link = $(this).find('link[rel="alternate"]').attr('href');
        var image = $(this).find('link[rel="image"]').attr('href');
        console.log("Article with title: " + title + " at " + link + " with image " + image);
    });
}