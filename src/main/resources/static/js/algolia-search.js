var search = instantsearch({
  appId: '06AOGMJW5Z',
  apiKey: '87476332c8c7436af730fa347c384b8c',
  indexName: 'staging_clothing',
  urlSync: true
});

var widgets = [
  instantsearch.widgets.searchBox({
    container: '#search-input',
    placeholder: 'Search for products'
  }),

  instantsearch.widgets.hits({
      container: '#hits',
      hitsPerPage: 18,
      templates: {
        item: getTemplate('hit'),
        empty: getTemplate('no-results')
      }
    }),

  instantsearch.widgets.stats({
    container: '#stats'
  }),

  instantsearch.widgets.sortBySelector({
      container: '#sort-by',
      autoHideContainer: true,
      indices: [{
        name: search.indexName, label: 'Most relevant'
      }, {
        name: search.indexName + '_instant_search_price_asc', label: 'Lowest price'
      }, {
        name: search.indexName + '_instant_search_price_desc', label: 'Highest price'
      }]
    }),

  instantsearch.widgets.pagination({
      container: '#pagination'
    }),

    instantsearch.widgets.refinementList({
      container: '#brand',
      attributeName: 'brand',
      limit: 10,
      operator: 'or',
      templates: {
        header: getHeader('Brand')
      }
    }),

    instantsearch.widgets.rangeSlider({
      container: '#price',
      attributeName: 'price',
      templates: {
        header: getHeader('Price')
      }
    }),

];

widgets.forEach(search.addWidget, search);

search.start();

function getTemplate(templateName) {
  return document.querySelector('#' + templateName + '-template').innerHTML;
}

function getHeader(title) {
  return '<h5>' + title + '</h5>';
}

function imageError(image) {
    $(image).parent().parent().parent().appendTo($(".ais-hits")).hide();
}