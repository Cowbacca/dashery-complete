var search = instantsearch({
  appId: '06AOGMJW5Z',
  apiKey: '87476332c8c7436af730fa347c384b8c',
  indexName: 'staging_clothing',
  urlSync: true
});

var widgets = [
  instantsearch.widgets.searchBox({
    container: '#search-input',
      placeholder: 'Search for products',
      cssClasses: {
          input: 'form-control'
      }
  }),

  instantsearch.widgets.hits({
      container: '#results',
      hitsPerPage: 9,
      templates: {
        item: getTemplate('hit'),
        empty: getTemplate('no-results')
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

function getHeader(title) {
  return '<h5>' + title + '</h5>';
}

function imageError(image) {
    $(image).parent().parent().parent().appendTo($(".ais-hits")).hide();
}