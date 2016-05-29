var search = instantsearch({
  appId: appId,
  apiKey: apiKey,
  indexName: indexName,
  urlSync: true
});

var widgets = [
  instantsearch.widgets.searchBox({
    container: '#search-input',
      poweredBy: true,
      placeholder: 'Search for products',
      cssClasses: {
          input: 'search form-control',
          poweredBy: 'powered-by'
      }
  }),

  instantsearch.widgets.hits({
      container: '#results',
      hitsPerPage: 9,
      templates: {
        item: getTemplate('hit'),
        empty: getTemplate('no-results')
      },
      transformData: {
          item: function (item) {
              item.price = '£' + (item.price / 100);
              return item;
          }
      }
    }),

  instantsearch.widgets.pagination({
      container: '#pagination'
  })

];

widgets.forEach(search.addWidget, search);

search.start();

function getTemplate(templateName) {
  return document.querySelector('#' + templateName + '-template').innerHTML;
}

function imageError(image) {
    $(image).parent().parent().parent().appendTo($(".ais-hits")).hide();
}