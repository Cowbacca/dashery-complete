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
        
        var article = '<div class="col-md-3 no-padding article">'
            + '<a href="'+link+'">'
                    + '<img src="'+image+'" alt="'+title+'"/>'
                    + '<div class="article-info">'
                            + '<h3>'+title+'</h3>'
                            + '<p>Some text here.</p>'
                    + '</div>'
                + '</a>'
            + '</div>';
            
        $("#articles").append(article);
    });
}