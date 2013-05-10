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
es.addEventListener('end-message', function(e) {
  window.history.pushState({"message": $("#message").html(),
                            "title": document.title,
                            "results": $("#results").html()},
                           null, e.data);
});
es.onmessage = function(e) {
  $('#message').html(e.data + "\n");
};
es.onerror = function(e) {
  $('.alert-box').show();
  $('#error').html(e.data);
};

$(function() {
  window.addEventListener("popstate", function(e) {
    // guard against initial page load in chrome
    if (e.state) {
      $("#message").html(e.state.message);
      $("#results").html(e.state.results);
      $("h1").html(e.state.title);
      document.title = e.state.title;
    }
  });

  var fetchUserContributions = function(user) {
    $.post('/contributions', {id: clientId, user: user});
    $('#results').show();
    $('#message').show();
    $('#message').html('Fetching repositories...');
    $('#user').val('');
    $('tbody').html('');
    document.title = user + "'s Github Contributions"
    $('h1').html(document.title);
  };

  $("form").on('submit', function(e) {
    fetchUserContributions($("#user").val());
    e.preventDefault();
  });

  // close alert box
  $('a.close').on('click', function(e) { $(e.target).parent().hide() });

  $('a.user').on('click', function(e) {
    fetchUserContributions(e.target.text);
    e.preventDefault;
  });

  var match;
  if (match = location.pathname.match(/^\/(.+)/)) {
    // Allow time for sse to register
    setTimeout(function() {fetchUserContributions(match[1])},
               500);
  } else {
    window.history.pushState({"message": '', "results": '', "title": $("h1").html()},
                             null,
                             '');
  }
});
