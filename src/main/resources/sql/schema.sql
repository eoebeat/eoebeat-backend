CREATE TABLE IF NOT EXISTS `music` (
                       `id` varchar(20) NOT NULL,
                       `update_time` bigint NOT NULL COMMENT '更新日期(timestamp)',
                       `song_date` date NOT NULL COMMENT '歌曲时间(YYYY.MM.DD)',
                       `singer` varchar(50) NOT NULL COMMENT '演唱者(空格分隔)',
                       `song_name` varchar(50) NOT NULL,
                       `song_name_alias` varchar(50) NOT NULL DEFAULT '',
                       `version_remark` varchar(50) DEFAULT NULL COMMENT '版本备注',
                       `duration` int NOT NULL COMMENT '音频时长(s)',
                       `Alist_audio_path` varchar(50) NOT NULL COMMENT '不含前后缀及歌曲名',
                       `Alist_cover_path` varchar(50) NOT NULL COMMENT '同上',
                       `audio_media_type` varchar(10) NOT NULL,
                       `cover_media_type` varchar(10) NOT NULL,
                       `song_status` varchar(10) DEFAULT NULL,
                       `partial_url` varchar(800) NOT NULL COMMENT '经过转义',
                       `hit_count` int NOT NULL DEFAULT '0',
                       `song_language` varchar(10) NOT NULL,
                       `has_cover` bit(1) NOT NULL,
                       `insert_time` timestamp(3) NOT NULL COMMENT '歌曲入库时间',
                       PRIMARY KEY (`id`),
                       KEY `song_date__index` (`song_date`),
                       KEY `hit_count__index` (`hit_count`),
                       KEY `singer__index` (`singer`),
                       KEY `song_name__index` (`song_name`),
                       KEY `song_name_alias__index` (`song_name_alias`),
                       KEY `insert_time__index` (`insert_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `user`
(
  `id`            int           NOT NULL AUTO_INCREMENT,
  `username`      varchar(200)  NOT NULL,
  `email`         varchar(200) DEFAULT NULL,
  `phone_country` varchar(10)   NOT NULL,
  `phone_number`  varchar(20)   NOT NULL,
  `playlist_ids`  varchar(1000) NOT NULL,
  `create_time`   timestamp     NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `playlist`
(
  `id`            int          NOT NULL AUTO_INCREMENT,
  `playlist_name` varchar(200) NOT NULL,
  `music_ids`     mediumtext,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `asset_config`
(
  `id`   int         NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `path` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `monthly_selection`
(
  `id`          int          NOT NULL AUTO_INCREMENT,
  `name`        varchar(30)  NOT NULL COMMENT '2023年3月',
  `cover_path`   varchar(50)  NOT NULL,
  `description` varchar(500) NOT NULL,
  `search_label` varchar(30) DEFAULT NULL COMMENT '2023.03',
  PRIMARY KEY (`id`),
  UNIQUE KEY `monthly_collection_name_uindex` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;