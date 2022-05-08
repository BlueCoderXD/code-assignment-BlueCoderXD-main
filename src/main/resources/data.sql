insert into post (id, title, content) values
    (100, 'Welcome', 'Welcome to this assignment'),
    (200, 'Assignment', 'Implement the missing pieces'),
    (300, 'Task', 'Add support for comments on a post');
insert into comment (id, post_id, comment) values
    (4, 100, 'Kilroy was here'),
    (5, 100, 'Foobar too');
