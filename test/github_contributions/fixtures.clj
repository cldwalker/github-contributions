(ns github-contributions.fixtures)

(def response-403
  {:trace-redirects ["https://api.github.com/repos/aichallenge/aichallenge/contributors"], :status 403, :headers {"access-control-allow-origin" "*", "server" "GitHub.com", "content-encoding" "gzip", "content-type" "application/json; charset=utf-8", "date" "Wed, 01 May 2013 22:37:21 GMT", "x-ratelimit-remaining" "0", "status" "403 Forbidden", "x-content-type-options" "nosniff", "x-ratelimit-limit" "60", "transfer-encoding" "chunked", "access-control-expose-headers" "Link, X-RateLimit-Limit, X-RateLimit-Remaining, X-OAuth-Scopes, X-Accepted-OAuth-Scopes", "x-github-media-type" "github.beta; format=json", "connection" "close", "access-control-allow-credentials" "true"}, :body {:message "API Rate Limit Exceeded for 127.0.0.1"}})

(def response-404
  {:trace-redirects ["https://api.github.com/repos/nko3/kodowa/contributors"], :status 404, :headers {"access-control-allow-origin" "*", "server" "GitHub.com", "content-encoding" "gzip", "content-type" "application/json; charset=utf-8", "date" "Wed, 01 May 2013 21:56:10 GMT", "x-ratelimit-remaining" "5", "status" "404 Not Found", "x-content-type-options" "nosniff", "x-ratelimit-limit" "60", "transfer-encoding" "chunked", "access-control-expose-headers" "Link, X-RateLimit-Limit, X-RateLimit-Remaining, X-OAuth-Scopes, X-Accepted-OAuth-Scopes", "x-github-media-type" "github.beta; format=json", "connection" "close", "access-control-allow-credentials" "true"}, :body {:message "Not Found"}})

(def response-user-repos
  [{:archive_url "https://api.github.com/repos/defunkt/ace/{archive_format}{/ref}", :has_issues false, :notifications_url "https://api.github.com/repos/defunkt/ace/notifications{?since,all,participating}", :forks_count 4, :git_tags_url "https://api.github.com/repos/defunkt/ace/git/tags{/sha}", :issue_comment_url "https://api.github.com/repos/defunkt/ace/issues/comments/{number}", :contributors_url "https://api.github.com/repos/defunkt/ace/contributors", :compare_url "https://api.github.com/repos/defunkt/ace/compare/{base}...{head}", :fork true, :labels_url "https://api.github.com/repos/defunkt/ace/labels{/name}", :collaborators_url "https://api.github.com/repos/defunkt/ace/collaborators{/collaborator}", :pushed_at "2011-11-16T18:37:42Z", :git_commits_url "https://api.github.com/repos/defunkt/ace/git/commits{/sha}", :trees_url "https://api.github.com/repos/defunkt/ace/git/trees{/sha}", :name "ace", :default_branch "master", :clone_url "https://github.com/defunkt/ace.git", :hooks_url "https://api.github.com/repos/defunkt/ace/hooks", :watchers 11, :updated_at "2013-04-05T15:46:51Z", :assignees_url "https://api.github.com/repos/defunkt/ace/assignees{/user}", :has_wiki true, :stargazers_url "https://api.github.com/repos/defunkt/ace/stargazers", :html_url "https://github.com/defunkt/ace", :teams_url "https://api.github.com/repos/defunkt/ace/teams", :git_refs_url "https://api.github.com/repos/defunkt/ace/git/refs{/sha}", :milestones_url "https://api.github.com/repos/defunkt/ace/milestones{/number}", :owner {:following_url "https://api.github.com/users/defunkt/following{/other_user}", :gists_url "https://api.github.com/users/defunkt/gists{/gist_id}", :starred_url "https://api.github.com/users/defunkt/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/defunkt/followers", :gravatar_id "b8dbb1987e8e5318584865f880036796", :avatar_url "https://secure.gravatar.com/avatar/b8dbb1987e8e5318584865f880036796?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", :html_url "https://github.com/defunkt", :received_events_url "https://api.github.com/users/defunkt/received_events", :login "defunkt", :url "https://api.github.com/users/defunkt", :organizations_url "https://api.github.com/users/defunkt/orgs", :type "User", :events_url "https://api.github.com/users/defunkt/events{/privacy}", :repos_url "https://api.github.com/users/defunkt/repos", :id 2, :subscriptions_url "https://api.github.com/users/defunkt/subscriptions"}, :language "JavaScript", :merges_url "https://api.github.com/repos/defunkt/ace/merges", :size 112, :created_at "2011-06-07T18:41:40Z", :branches_url "https://api.github.com/repos/defunkt/ace/branches{/branch}", :issues_url "https://api.github.com/repos/defunkt/ace/issues{/number}", :private false, :homepage "http://ace.ajax.org", :git_url "git://github.com/defunkt/ace.git", :mirror_url nil, :url "https://api.github.com/repos/defunkt/ace", :issue_events_url "https://api.github.com/repos/defunkt/ace/issues/events{/number}", :subscribers_url "https://api.github.com/repos/defunkt/ace/subscribers", :has_downloads true, :full_name "defunkt/ace", :watchers_count 11, :statuses_url "https://api.github.com/repos/defunkt/ace/statuses/{sha}", :permissions {:admin false, :push false, :pull true}, :open_issues_count 0, :master_branch "master", :ssh_url "git@github.com:defunkt/ace.git", :languages_url "https://api.github.com/repos/defunkt/ace/languages", :commits_url "https://api.github.com/repos/defunkt/ace/commits{/sha}", :forks_url "https://api.github.com/repos/defunkt/ace/forks", :subscription_url "https://api.github.com/repos/defunkt/ace/subscription", :contents_url "https://api.github.com/repos/defunkt/ace/contents/{+path}", :events_url "https://api.github.com/repos/defunkt/ace/events", :tags_url "https://api.github.com/repos/defunkt/ace/tags", :open_issues 0, :id 1861402, :forks 4, :svn_url "https://github.com/defunkt/ace", :downloads_url "https://api.github.com/repos/defunkt/ace/downloads", :blobs_url "https://api.github.com/repos/defunkt/ace/git/blobs{/sha}", :description "Ajax.org Cloud9 Editor", :pulls_url "https://api.github.com/repos/defunkt/ace/pulls{/number}", :comments_url "https://api.github.com/repos/defunkt/ace/comments{/number}", :keys_url "https://api.github.com/repos/defunkt/ace/keys{/key_id}"}

   {:archive_url "https://api.github.com/repos/defunkt/acts_as_textiled/{archive_format}{/ref}", :has_issues false, :notifications_url "https://api.github.com/repos/defunkt/acts_as_textiled/notifications{?since,all,participating}", :forks_count 31, :git_tags_url "https://api.github.com/repos/defunkt/acts_as_textiled/git/tags{/sha}", :issue_comment_url "https://api.github.com/repos/defunkt/acts_as_textiled/issues/comments/{number}", :contributors_url "https://api.github.com/repos/defunkt/acts_as_textiled/contributors", :compare_url "https://api.github.com/repos/defunkt/acts_as_textiled/compare/{base}...{head}", :fork false, :labels_url "https://api.github.com/repos/defunkt/acts_as_textiled/labels{/name}", :collaborators_url "https://api.github.com/repos/defunkt/acts_as_textiled/collaborators{/collaborator}", :pushed_at "2011-07-21T21:38:47Z", :git_commits_url "https://api.github.com/repos/defunkt/acts_as_textiled/git/commits{/sha}", :trees_url "https://api.github.com/repos/defunkt/acts_as_textiled/git/trees{/sha}", :name "acts_as_textiled", :default_branch "master", :clone_url "https://github.com/defunkt/acts_as_textiled.git", :hooks_url "https://api.github.com/repos/defunkt/acts_as_textiled/hooks", :watchers 118, :updated_at "2013-05-01T23:45:04Z", :assignees_url "https://api.github.com/repos/defunkt/acts_as_textiled/assignees{/user}", :has_wiki false, :stargazers_url "https://api.github.com/repos/defunkt/acts_as_textiled/stargazers", :html_url "https://github.com/defunkt/acts_as_textiled", :teams_url "https://api.github.com/repos/defunkt/acts_as_textiled/teams", :git_refs_url "https://api.github.com/repos/defunkt/acts_as_textiled/git/refs{/sha}", :milestones_url "https://api.github.com/repos/defunkt/acts_as_textiled/milestones{/number}", :owner {:following_url "https://api.github.com/users/defunkt/following{/other_user}", :gists_url "https://api.github.com/users/defunkt/gists{/gist_id}", :starred_url "https://api.github.com/users/defunkt/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/defunkt/followers", :gravatar_id "b8dbb1987e8e5318584865f880036796", :avatar_url "https://secure.gravatar.com/avatar/b8dbb1987e8e5318584865f880036796?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", :html_url "https://github.com/defunkt", :received_events_url "https://api.github.com/users/defunkt/received_events", :login "defunkt", :url "https://api.github.com/users/defunkt", :organizations_url "https://api.github.com/users/defunkt/orgs", :type "User", :events_url "https://api.github.com/users/defunkt/events{/privacy}", :repos_url "https://api.github.com/users/defunkt/repos", :id 2, :subscriptions_url "https://api.github.com/users/defunkt/subscriptions"}, :language "Ruby", :merges_url "https://api.github.com/repos/defunkt/acts_as_textiled/merges", :size 204, :created_at "2008-03-12T06:20:18Z", :branches_url "https://api.github.com/repos/defunkt/acts_as_textiled/branches{/branch}", :issues_url "https://api.github.com/repos/defunkt/acts_as_textiled/issues{/number}", :private false, :homepage "http://errtheblog.com/posts/12-actsastextiled", :git_url "git://github.com/defunkt/acts_as_textiled.git", :mirror_url nil, :url "https://api.github.com/repos/defunkt/acts_as_textiled", :issue_events_url "https://api.github.com/repos/defunkt/acts_as_textiled/issues/events{/number}", :subscribers_url "https://api.github.com/repos/defunkt/acts_as_textiled/subscribers", :has_downloads false, :full_name "defunkt/acts_as_textiled", :watchers_count 118, :statuses_url "https://api.github.com/repos/defunkt/acts_as_textiled/statuses/{sha}", :permissions {:admin false, :push false, :pull true}, :open_issues_count 4, :master_branch "master", :ssh_url "git@github.com:defunkt/acts_as_textiled.git", :languages_url "https://api.github.com/repos/defunkt/acts_as_textiled/languages", :commits_url "https://api.github.com/repos/defunkt/acts_as_textiled/commits{/sha}", :forks_url "https://api.github.com/repos/defunkt/acts_as_textiled/forks", :subscription_url "https://api.github.com/repos/defunkt/acts_as_textiled/subscription", :contents_url "https://api.github.com/repos/defunkt/acts_as_textiled/contents/{+path}", :events_url "https://api.github.com/repos/defunkt/acts_as_textiled/events", :tags_url "https://api.github.com/repos/defunkt/acts_as_textiled/tags", :open_issues 4, :id 3594, :forks 31, :svn_url "https://github.com/defunkt/acts_as_textiled", :downloads_url "https://api.github.com/repos/defunkt/acts_as_textiled/downloads", :blobs_url "https://api.github.com/repos/defunkt/acts_as_textiled/git/blobs{/sha}", :description "Makes your models act as textiled.", :pulls_url "https://api.github.com/repos/defunkt/acts_as_textiled/pulls{/number}", :comments_url "https://api.github.com/repos/defunkt/acts_as_textiled/comments{/number}", :keys_url "https://api.github.com/repos/defunkt/acts_as_textiled/keys{/key_id}"}])

(def response-specific-repo
  {:archive_url "https://api.github.com/repos/defunkt/ace/{archive_format}{/ref}", :has_issues false, :notifications_url "https://api.github.com/repos/defunkt/ace/notifications{?since,all,participating}", :forks_count 4, :git_tags_url "https://api.github.com/repos/defunkt/ace/git/tags{/sha}", :parent {:archive_url "https://api.github.com/repos/ajaxorg/ace/{archive_format}{/ref}", :has_issues true, :notifications_url "https://api.github.com/repos/ajaxorg/ace/notifications{?since,all,participating}", :forks_count 1166, :git_tags_url "https://api.github.com/repos/ajaxorg/ace/git/tags{/sha}", :issue_comment_url "https://api.github.com/repos/ajaxorg/ace/issues/comments/{number}", :contributors_url "https://api.github.com/repos/ajaxorg/ace/contributors", :compare_url "https://api.github.com/repos/ajaxorg/ace/compare/{base}...{head}", :fork false, :labels_url "https://api.github.com/repos/ajaxorg/ace/labels{/name}", :collaborators_url "https://api.github.com/repos/ajaxorg/ace/collaborators{/collaborator}", :pushed_at "2013-05-13T07:17:05Z", :git_commits_url "https://api.github.com/repos/ajaxorg/ace/git/commits{/sha}", :trees_url "https://api.github.com/repos/ajaxorg/ace/git/trees{/sha}", :name "ace", :default_branch "master", :clone_url "https://github.com/ajaxorg/ace.git", :hooks_url "https://api.github.com/repos/ajaxorg/ace/hooks", :watchers 5188, :updated_at "2013-05-14T11:11:55Z", :assignees_url "https://api.github.com/repos/ajaxorg/ace/assignees{/user}", :has_wiki true, :stargazers_url "https://api.github.com/repos/ajaxorg/ace/stargazers", :html_url "https://github.com/ajaxorg/ace", :teams_url "https://api.github.com/repos/ajaxorg/ace/teams", :git_refs_url "https://api.github.com/repos/ajaxorg/ace/git/refs{/sha}", :milestones_url "https://api.github.com/repos/ajaxorg/ace/milestones{/number}", :owner {:following_url "https://api.github.com/users/ajaxorg/following{/other_user}", :gists_url "https://api.github.com/users/ajaxorg/gists{/gist_id}", :starred_url "https://api.github.com/users/ajaxorg/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/ajaxorg/followers", :gravatar_id "fc7dd0ffdb5290c7e473e08e14a31daa", :avatar_url "https://secure.gravatar.com/avatar/fc7dd0ffdb5290c7e473e08e14a31daa?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-org-420.png", :html_url "https://github.com/ajaxorg", :received_events_url "https://api.github.com/users/ajaxorg/received_events", :login "ajaxorg", :url "https://api.github.com/users/ajaxorg", :organizations_url "https://api.github.com/users/ajaxorg/orgs", :type "Organization", :events_url "https://api.github.com/users/ajaxorg/events{/privacy}", :repos_url "https://api.github.com/users/ajaxorg/repos", :id 168515, :subscriptions_url "https://api.github.com/users/ajaxorg/subscriptions"}, :language "JavaScript", :merges_url "https://api.github.com/repos/ajaxorg/ace/merges", :size 11802, :created_at "2010-10-27T10:43:36Z", :branches_url "https://api.github.com/repos/ajaxorg/ace/branches{/branch}", :issues_url "https://api.github.com/repos/ajaxorg/ace/issues{/number}", :private false, :homepage "http://ace.ajax.org", :git_url "git://github.com/ajaxorg/ace.git", :mirror_url nil, :url "https://api.github.com/repos/ajaxorg/ace", :issue_events_url "https://api.github.com/repos/ajaxorg/ace/issues/events{/number}", :subscribers_url "https://api.github.com/repos/ajaxorg/ace/subscribers", :has_downloads true, :full_name "ajaxorg/ace", :watchers_count 5188, :statuses_url "https://api.github.com/repos/ajaxorg/ace/statuses/{sha}", :open_issues_count 142, :master_branch "master", :ssh_url "git@github.com:ajaxorg/ace.git", :languages_url "https://api.github.com/repos/ajaxorg/ace/languages", :commits_url "https://api.github.com/repos/ajaxorg/ace/commits{/sha}", :forks_url "https://api.github.com/repos/ajaxorg/ace/forks", :subscription_url "https://api.github.com/repos/ajaxorg/ace/subscription", :contents_url "https://api.github.com/repos/ajaxorg/ace/contents/{+path}", :events_url "https://api.github.com/repos/ajaxorg/ace/events", :tags_url "https://api.github.com/repos/ajaxorg/ace/tags", :open_issues 142, :id 1028340, :forks 1166, :svn_url "https://github.com/ajaxorg/ace", :downloads_url "https://api.github.com/repos/ajaxorg/ace/downloads", :blobs_url "https://api.github.com/repos/ajaxorg/ace/git/blobs{/sha}", :description "Ajax.org Cloud9 Editor", :pulls_url "https://api.github.com/repos/ajaxorg/ace/pulls{/number}", :comments_url "https://api.github.com/repos/ajaxorg/ace/comments{/number}", :keys_url "https://api.github.com/repos/ajaxorg/ace/keys{/key_id}"}, :issue_comment_url "https://api.github.com/repos/defunkt/ace/issues/comments/{number}", :contributors_url "https://api.github.com/repos/defunkt/ace/contributors", :compare_url "https://api.github.com/repos/defunkt/ace/compare/{base}...{head}", :fork true, :labels_url "https://api.github.com/repos/defunkt/ace/labels{/name}", :collaborators_url "https://api.github.com/repos/defunkt/ace/collaborators{/collaborator}", :pushed_at "2011-11-16T18:37:42Z", :git_commits_url "https://api.github.com/repos/defunkt/ace/git/commits{/sha}", :trees_url "https://api.github.com/repos/defunkt/ace/git/trees{/sha}", :name "ace", :default_branch "master", :clone_url "https://github.com/defunkt/ace.git", :hooks_url "https://api.github.com/repos/defunkt/ace/hooks", :watchers 11, :updated_at "2013-04-05T15:46:51Z", :assignees_url "https://api.github.com/repos/defunkt/ace/assignees{/user}", :has_wiki true, :stargazers_url "https://api.github.com/repos/defunkt/ace/stargazers", :html_url "https://github.com/defunkt/ace", :teams_url "https://api.github.com/repos/defunkt/ace/teams", :git_refs_url "https://api.github.com/repos/defunkt/ace/git/refs{/sha}", :milestones_url "https://api.github.com/repos/defunkt/ace/milestones{/number}", :network_count 1166, :owner {:following_url "https://api.github.com/users/defunkt/following{/other_user}", :gists_url "https://api.github.com/users/defunkt/gists{/gist_id}", :starred_url "https://api.github.com/users/defunkt/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/defunkt/followers", :gravatar_id "b8dbb1987e8e5318584865f880036796", :avatar_url "https://secure.gravatar.com/avatar/b8dbb1987e8e5318584865f880036796?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", :html_url "https://github.com/defunkt", :received_events_url "https://api.github.com/users/defunkt/received_events", :login "defunkt", :url "https://api.github.com/users/defunkt", :organizations_url "https://api.github.com/users/defunkt/orgs", :type "User", :events_url "https://api.github.com/users/defunkt/events{/privacy}", :repos_url "https://api.github.com/users/defunkt/repos", :id 2, :subscriptions_url "https://api.github.com/users/defunkt/subscriptions"}, :language "JavaScript", :merges_url "https://api.github.com/repos/defunkt/ace/merges", :size 112, :created_at "2011-06-07T18:41:40Z", :branches_url "https://api.github.com/repos/defunkt/ace/branches{/branch}", :issues_url "https://api.github.com/repos/defunkt/ace/issues{/number}", :private false, :homepage "http://ace.ajax.org", :git_url "git://github.com/defunkt/ace.git", :mirror_url nil, :url "https://api.github.com/repos/defunkt/ace", :issue_events_url "https://api.github.com/repos/defunkt/ace/issues/events{/number}", :subscribers_url "https://api.github.com/repos/defunkt/ace/subscribers", :has_downloads true, :full_name "defunkt/ace", :watchers_count 11, :statuses_url "https://api.github.com/repos/defunkt/ace/statuses/{sha}", :permissions {:admin false, :push false, :pull true}, :open_issues_count 0, :master_branch "master", :ssh_url "git@github.com:defunkt/ace.git", :languages_url "https://api.github.com/repos/defunkt/ace/languages", :commits_url "https://api.github.com/repos/defunkt/ace/commits{/sha}", :forks_url "https://api.github.com/repos/defunkt/ace/forks", :subscription_url "https://api.github.com/repos/defunkt/ace/subscription", :contents_url "https://api.github.com/repos/defunkt/ace/contents/{+path}", :events_url "https://api.github.com/repos/defunkt/ace/events", :tags_url "https://api.github.com/repos/defunkt/ace/tags", :open_issues 0, :source {:archive_url "https://api.github.com/repos/ajaxorg/ace/{archive_format}{/ref}", :has_issues true, :notifications_url "https://api.github.com/repos/ajaxorg/ace/notifications{?since,all,participating}", :forks_count 1166, :git_tags_url "https://api.github.com/repos/ajaxorg/ace/git/tags{/sha}", :issue_comment_url "https://api.github.com/repos/ajaxorg/ace/issues/comments/{number}", :contributors_url "https://api.github.com/repos/ajaxorg/ace/contributors", :compare_url "https://api.github.com/repos/ajaxorg/ace/compare/{base}...{head}", :fork false, :labels_url "https://api.github.com/repos/ajaxorg/ace/labels{/name}", :collaborators_url "https://api.github.com/repos/ajaxorg/ace/collaborators{/collaborator}", :pushed_at "2013-05-13T07:17:05Z", :git_commits_url "https://api.github.com/repos/ajaxorg/ace/git/commits{/sha}", :trees_url "https://api.github.com/repos/ajaxorg/ace/git/trees{/sha}", :name "ace", :default_branch "master", :clone_url "https://github.com/ajaxorg/ace.git", :hooks_url "https://api.github.com/repos/ajaxorg/ace/hooks", :watchers 5188, :updated_at "2013-05-14T11:11:55Z", :assignees_url "https://api.github.com/repos/ajaxorg/ace/assignees{/user}", :has_wiki true, :stargazers_url "https://api.github.com/repos/ajaxorg/ace/stargazers", :html_url "https://github.com/ajaxorg/ace", :teams_url "https://api.github.com/repos/ajaxorg/ace/teams", :git_refs_url "https://api.github.com/repos/ajaxorg/ace/git/refs{/sha}", :milestones_url "https://api.github.com/repos/ajaxorg/ace/milestones{/number}", :owner {:following_url "https://api.github.com/users/ajaxorg/following{/other_user}", :gists_url "https://api.github.com/users/ajaxorg/gists{/gist_id}", :starred_url "https://api.github.com/users/ajaxorg/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/ajaxorg/followers", :gravatar_id "fc7dd0ffdb5290c7e473e08e14a31daa", :avatar_url "https://secure.gravatar.com/avatar/fc7dd0ffdb5290c7e473e08e14a31daa?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-org-420.png", :html_url "https://github.com/ajaxorg", :received_events_url "https://api.github.com/users/ajaxorg/received_events", :login "ajaxorg", :url "https://api.github.com/users/ajaxorg", :organizations_url "https://api.github.com/users/ajaxorg/orgs", :type "Organization", :events_url "https://api.github.com/users/ajaxorg/events{/privacy}", :repos_url "https://api.github.com/users/ajaxorg/repos", :id 168515, :subscriptions_url "https://api.github.com/users/ajaxorg/subscriptions"}, :language "JavaScript", :merges_url "https://api.github.com/repos/ajaxorg/ace/merges", :size 11802, :created_at "2010-10-27T10:43:36Z", :branches_url "https://api.github.com/repos/ajaxorg/ace/branches{/branch}", :issues_url "https://api.github.com/repos/ajaxorg/ace/issues{/number}", :private false, :homepage "http://ace.ajax.org", :git_url "git://github.com/ajaxorg/ace.git", :mirror_url nil, :url "https://api.github.com/repos/ajaxorg/ace", :issue_events_url "https://api.github.com/repos/ajaxorg/ace/issues/events{/number}", :subscribers_url "https://api.github.com/repos/ajaxorg/ace/subscribers", :has_downloads true, :full_name "ajaxorg/ace", :watchers_count 5188, :statuses_url "https://api.github.com/repos/ajaxorg/ace/statuses/{sha}", :open_issues_count 142, :master_branch "master", :ssh_url "git@github.com:ajaxorg/ace.git", :languages_url "https://api.github.com/repos/ajaxorg/ace/languages", :commits_url "https://api.github.com/repos/ajaxorg/ace/commits{/sha}", :forks_url "https://api.github.com/repos/ajaxorg/ace/forks", :subscription_url "https://api.github.com/repos/ajaxorg/ace/subscription", :contents_url "https://api.github.com/repos/ajaxorg/ace/contents/{+path}", :events_url "https://api.github.com/repos/ajaxorg/ace/events", :tags_url "https://api.github.com/repos/ajaxorg/ace/tags", :open_issues 142, :id 1028340, :forks 1166, :svn_url "https://github.com/ajaxorg/ace", :downloads_url "https://api.github.com/repos/ajaxorg/ace/downloads", :blobs_url "https://api.github.com/repos/ajaxorg/ace/git/blobs{/sha}", :description "Ajax.org Cloud9 Editor", :pulls_url "https://api.github.com/repos/ajaxorg/ace/pulls{/number}", :comments_url "https://api.github.com/repos/ajaxorg/ace/comments{/number}", :keys_url "https://api.github.com/repos/ajaxorg/ace/keys{/key_id}"}, :id 1861402, :forks 4, :svn_url "https://github.com/defunkt/ace", :downloads_url "https://api.github.com/repos/defunkt/ace/downloads", :blobs_url "https://api.github.com/repos/defunkt/ace/git/blobs{/sha}", :description "Ajax.org Cloud9 Editor", :pulls_url "https://api.github.com/repos/defunkt/ace/pulls{/number}", :comments_url "https://api.github.com/repos/defunkt/ace/comments{/number}", :keys_url "https://api.github.com/repos/defunkt/ace/keys{/key_id}"})

;; only took 46 and 47th elements to shorten response
(def response-contributors
  [{:following_url "https://api.github.com/users/NalaGinrut/following{/other_user}", :gists_url "https://api.github.com/users/NalaGinrut/gists{/gist_id}", :starred_url "https://api.github.com/users/NalaGinrut/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/NalaGinrut/followers", :gravatar_id "9a606d9c4e90d963463107046aa61e70", :contributions 4, :avatar_url "https://secure.gravatar.com/avatar/9a606d9c4e90d963463107046aa61e70?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", :html_url "https://github.com/NalaGinrut", :received_events_url "https://api.github.com/users/NalaGinrut/received_events", :login "NalaGinrut", :url "https://api.github.com/users/NalaGinrut", :organizations_url "https://api.github.com/users/NalaGinrut/orgs", :type "User", :events_url "https://api.github.com/users/NalaGinrut/events{/privacy}", :repos_url "https://api.github.com/users/NalaGinrut/repos", :id 19734, :subscriptions_url "https://api.github.com/users/NalaGinrut/subscriptions"}
   {:following_url "https://api.github.com/users/defunkt/following{/other_user}", :gists_url "https://api.github.com/users/defunkt/gists{/gist_id}", :starred_url "https://api.github.com/users/defunkt/starred{/owner}{/repo}", :followers_url "https://api.github.com/users/defunkt/followers", :gravatar_id "b8dbb1987e8e5318584865f880036796", :contributions 5, :avatar_url "https://secure.gravatar.com/avatar/b8dbb1987e8e5318584865f880036796?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png", :html_url "https://github.com/defunkt", :received_events_url "https://api.github.com/users/defunkt/received_events", :login "defunkt", :url "https://api.github.com/users/defunkt", :organizations_url "https://api.github.com/users/defunkt/orgs", :type "User", :events_url "https://api.github.com/users/defunkt/events{/privacy}", :repos_url "https://api.github.com/users/defunkt/repos", :id 2, :subscriptions_url "https://api.github.com/users/defunkt/subscriptions"}
   {}])