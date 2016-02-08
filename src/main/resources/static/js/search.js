function initSearchTokenfield() {
  var engine = new Bloodhound({
        remote: {url: location.protocol + '//' + location.host + '/tokens/%QUERY', wildcard: '%QUERY'},
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
    });

    var newLength;
    var oldLength;

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

    .on('tokenfield:createdtoken tokenfield:removedtoken', function (e) {
        newLength = $('.tokenfield-search').tokenfield('getTokens').length;
    })

    .parent().on('keydown keypress', this, function (event) {
        if(event.keyCode === 13) {
            event.preventDefault();

            if(oldLength === newLength) {
                search();
            }
        }
        oldLength = $('.tokenfield-search').tokenfield('getTokens').length;
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
