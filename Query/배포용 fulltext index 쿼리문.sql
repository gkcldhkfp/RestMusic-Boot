ALTER TABLE songs DROP INDEX song_title_idx;
ALTER TABLE albums DROP INDEX albums_title_idx;
ALTER TABLE artists DROP INDEX artists_name_idx;
ALTER TABLE `groups` DROP INDEX group_name_idx;

ALTER TABLE songs ADD FULLTEXT INDEX song_title_idx (title) WITH PARSER ngram;
ALTER TABLE albums ADD FULLTEXT INDEX albums_title_idx (album_name) WITH PARSER ngram;
ALTER TABLE artists ADD FULLTEXT INDEX artists_name_idx (artist_name) WITH PARSER ngram;
ALTER TABLE `groups` ADD FULLTEXT INDEX group_name_idx (group_name) WITH PARSER ngram;

SHOW VARIABLES LIKE 'ft_min_word_len';