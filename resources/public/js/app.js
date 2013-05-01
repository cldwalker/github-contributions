// from
// http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript/105074#105074
// not a true guid but good enough for now
function s4() {
  return Math.floor((1 + Math.random()) * 0x10000)
             .toString(16)
             .substring(1);
};

function guid() {
  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
         s4() + '-' + s4() + s4() + s4();
};

var clientId = guid();
var es = new EventSource('/contributions?id='+clientId);
es.addEventListener('results', function(e) {
  $("#results").append(e.data + "\n");
});
es.onmessage = function(e) {
  $('#message').html(e.data + "\n");
};

$("form").on('submit', function(e) {
  $.post('/contributions', {id: clientId, user: $("#user").val()});
  $('#message').html('Fetching repositories...');
  $('#user').val('');
  $('tbody').html('');
  e.preventDefault();
});
