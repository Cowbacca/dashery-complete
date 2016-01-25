function initSearchTokenfield() {
    var terms = [
                            {
                                category: 'Colour',
                                value: 'grey',
                            },
                            {
                                category: 'Type',
                                value: 'trousers',
                            },
                            {
                                category: 'Material',
                                value: 'wool',
                            },
                            {
                                category: 'Origin',
                                value: 'Made in England',
                            },
                        ];

    var engine = new Bloodhound({
        prefetch: prefetchURL,
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
    });

    engine.initialize();

    $('.tokenfield-search')

    .on('tokenfield:createtoken', function (e) {
        engine.search(e.attrs.value, addCategory, addCategory);

        function addCategory(datums) {
            if(!e.attrs.category && datums.length > 0){
                e.attrs.category = datums[0].category;
            }
        }
      })

    .tokenfield({
        typeahead: [null,
            {
                source: engine.ttAdapter(),
                name: 'clothes',
                display: 'value',
                templates: {
                    suggestion: Handlebars.compile('<div>{{category}}: {{value}}</div>')
                },
            }
        ]
    })

    .parent().on('keydown', this, function (event) {
        if (event.ctrlKey && event.keyCode == 13) {
            search();
        }
    });
}

function sortNumericalResults(attribute) {
    sortResults(attribute, function (a, b) {
        return $(a).data(attribute) - $(b).data(attribute);
    })

}

function sortAlphabeticalResults(attribute) {
    sortResults(attribute, function(a, b) {
        return $(a).data(attribute) > $(b).data(attribute);
    })
}

function sortResults(attribute, sortFunction) {
  var results = $('.col-result');
    results.sort(sortFunction);

    if (sorted) {
        results = results.get().reverse();
    }

    $('#results').html(results);

    sorted = !sorted;
}
var sorted;

$(document).ready(function () {
    initSearchTokenfield.call(this);
    sorted = false;
});

function search() {
    var tokens = $('.tokenfield-search').tokenfield('getTokens');
    if(tokens.length > 0){
        var searchQuery = '?search='
            + $.map(tokens, function(token){
                return token.category + ":" + token.value;
            }).join(',');
        window.location.href = 'results' + searchQuery;
    } else {
        alert('Please enter some tokens!');
    }
}