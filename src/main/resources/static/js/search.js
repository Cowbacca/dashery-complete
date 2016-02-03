function initSearchTokenfield() {
  var engine = new Bloodhound({
        remote: {url: '/tokens/start-with/%QUERY', wildcard: '%QUERY'},
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
    });

    $('.tokenfield-search')
    .tokenfield({
        typeahead: [{
                      hint: true,
                      highlight: true,
                    },
            {
                source: engine.ttAdapter(),
                name: 'clothes',
                display: 'value',
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
                return token.value;
            }).join(',');
        window.location.href = 'results' + searchQuery;
    } else {
        alert('Please enter some tokens!');
    }
}
