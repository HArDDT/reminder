INSERT INTO users(
	email, name, admin, superadmin, password)
	VALUES ('dimatihiy@ukr.net', 'Dima Tihiy', true, true, '$2a$10$VH30dRRvt1BqtZDzitbiMu.6M/0VrDUsEqfrNU7eCYQnnsbV24/6y');

INSERT INTO users(
    email, name, admin, superadmin, password)
VALUES ('alex@gmail.com', 'Alex', true, false, '$2a$10$VH30dRRvt1BqtZDzitbiMu.6M/0VrDUsEqfrNU7eCYQnnsbV24/6y');

INSERT INTO users(
    email, name, admin, superadmin, password)
VALUES ('max@ukr.net', 'Max', false, false, '$2a$10$VH30dRRvt1BqtZDzitbiMu.6M/0VrDUsEqfrNU7eCYQnnsbV24/6y');

INSERT INTO users(
    email, name, admin, superadmin, password)
VALUES ('mike@ukr.net', 'Mike', false, false, '$2a$10$VH30dRRvt1BqtZDzitbiMu.6M/0VrDUsEqfrNU7eCYQnnsbV24/6y');



INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (1, 1, '1 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (2, 1, '3 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (3, 2, '4 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (4, 2, '5 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (5, 2, '6 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (6, 2, 'pgadmin', '2019-06-26 18:11:22', true, '1 week');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (7, 2, 'pgadmin', '2019-06-26 18:11:22', true, '1 week');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (8, 1, '6 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (9, 1, '6 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (10, 1, '6 event for test', '2019-06-12 20:56:14', true, '1, day');

INSERT INTO events(
    id, userid, description, eventdate, activeevent, reminderexpression)
VALUES (11, 1, '6 event for test', '2019-06-12 20:56:14', true, '1, day');