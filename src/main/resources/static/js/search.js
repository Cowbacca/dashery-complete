function initSearchTokenfield() {
  var engine = new Bloodhound({
        remote: {url: location.protocol + '//' + location.host + '/tokens/%QUERY', wildcard: '%QUERY'},
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
    });

    var blocked;

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
        ],
        delimiter: ', '
    })

    .on('tokenfield:createdtoken', function (e) {
            blocked=true;
        })

    .parent().on('keydown keypress click', this, function (event) {
        if(event.keyCode === 13 && $('.tt-input').val().length ===0 && !blocked) {
            search();
        }
        blocked = false;
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
    //http://stackoverflow.com/a/31412050
    var queryDict = {};
    location.search.substr(1).split("&").forEach(function(item) {queryDict[item.split("=")[0]] = item.split("=")[1]});

    if(queryDict["search"]) {
        $('.tokenfield-search').val(queryDict["search"]);
    }

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
