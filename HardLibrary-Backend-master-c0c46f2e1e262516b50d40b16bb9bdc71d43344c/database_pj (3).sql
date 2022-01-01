-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2022-01-02 07:00:13
-- 服务器版本： 5.7.26
-- PHP 版本： 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `database_pj`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--

CREATE TABLE `admin` (
  `id` bigint(100) NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- 表的结构 `department`
--

CREATE TABLE `department` (
  `id` int(100) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `manager_id` bigint(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `department`
--

INSERT INTO `department` (`id`, `name`, `manager_id`) VALUES
(1, '总裁办公室', NULL),
(2, '研究院', NULL),
(3, '人力资源部门', NULL),
(4, '产品研发部门', NULL),
(5, '营销部门', NULL),
(6, '推广部门', NULL),
(7, '人事管理部门', NULL),
(8, '测试部门', NULL),
(9, '策划部门', 10231106004),
(10, '技术部门', 10231106005);

-- --------------------------------------------------------

--
-- 表的结构 `employee`
--

CREATE TABLE `employee` (
  `id` bigint(100) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `department_id` bigint(10) NOT NULL,
  `department_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `sex` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `telephone_number` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `employ_date` date NOT NULL,
  `age` int(10) NOT NULL,
  `location` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `employee`
--

INSERT INTO `employee` (`id`, `name`, `department_id`, `department_name`, `username`, `password`, `sex`, `telephone_number`, `employ_date`, `age`, `location`, `email`) VALUES
(10231106002, '李耀', 8, '测试部门', 'liyao', '1234567', '男', '10000000000', '2021-12-01', 25, '上海交通大学', 'test@test.com'),
(10231106003, '王鑫', 10, '技术部门', 'wangxin', '123456', '男', '19820220101', '2007-05-20', 32, '光华楼30楼', 'test@example.com'),
(10231106004, '王倩', 9, '策划部门', 'wangqian', '123456', '女', '12312312346', '2009-01-05', 35, '南京', 'example@xx.com'),
(10231106005, '卢潇', 5, '营销部门', 'luxiao', '123456', '男', '11451419198', '2012-03-31', 28, '正大体育馆', 'test@fdu.com'),
(10231106124, '蒋玥', 10, '技术部门', 'jiangyue', '123456', '女', '12312312345', '2012-01-05', 29, '西安', 'example@xx.com'),
(10231106138, '宋雪', 8, '测试部门', 'songxue', '123456', '女', '10000000000', '2009-03-12', 27, '复旦大学', '200@fudan.edu.cn');

-- --------------------------------------------------------

--
-- 表的结构 `lesson`
--

CREATE TABLE `lesson` (
  `id` int(100) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `teacher_id` bigint(100) NOT NULL,
  `genre` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `lesson`
--

INSERT INTO `lesson` (`id`, `name`, `teacher_id`, `genre`, `description`) VALUES
(1, '复旦校歌学唱', 10231106124, '艺术', '直通129歌会'),
(35142, '数据库设计\r\n', 10231106124, '开发', 'xxxxxx'),
(35155, '软件测试', 10231106138, '测试', 'xxxxxxxx');

-- --------------------------------------------------------

--
-- 表的结构 `log_history`
--

CREATE TABLE `log_history` (
  `id` int(100) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `log` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `log_history`
--

INSERT INTO `log_history` (`id`, `username`, `log`, `date`) VALUES
(2, 'jiangyue', 'create new lesson', '2022-01-02');

-- --------------------------------------------------------

--
-- 表的结构 `selectable_lesson`
--

CREATE TABLE `selectable_lesson` (
  `id` bigint(100) NOT NULL,
  `lesson_id` bigint(100) NOT NULL,
  `department_id` bigint(100) NOT NULL,
  `type` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `selectable_lesson`
--

INSERT INTO `selectable_lesson` (`id`, `lesson_id`, `department_id`, `type`) VALUES
(21, 1, 9, '必修'),
(20, 1, 8, '必修'),
(22, 1, 1, '选修'),
(23, 1, 2, '选修'),
(24, 1, 3, '选修'),
(25, 1, 4, '选修'),
(26, 1, 5, '选修'),
(27, 1, 6, '选修'),
(28, 1, 7, '选修'),
(29, 1, 8, '选修'),
(30, 1, 9, '选修'),
(31, 1, 10, '选修');

-- --------------------------------------------------------

--
-- 表的结构 `test_history`
--

CREATE TABLE `test_history` (
  `id` int(200) NOT NULL,
  `tutor_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `belong_to` bigint(100) NOT NULL,
  `lesson` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lesson_id` bigint(20) NOT NULL,
  `grade` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` date NOT NULL,
  `status` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `test_history`
--

INSERT INTO `test_history` (`id`, `tutor_name`, `belong_to`, `lesson`, `lesson_id`, `grade`, `date`, `status`) VALUES
(17, '蒋玥', 10231106002, '复旦校歌学唱', 1, NULL, '2022-01-02', '修读中'),
(18, '蒋玥', 10231106138, '复旦校歌学唱', 1, NULL, '2022-01-02', '修读中');

-- --------------------------------------------------------

--
-- 表的结构 `tutor`
--

CREATE TABLE `tutor` (
  `id` bigint(100) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `tutor_date` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `tutor`
--

INSERT INTO `tutor` (`id`, `name`, `tutor_date`) VALUES
(10231106124, '蒋玥', '2010-01-01');

--
-- 转储表的索引
--

--
-- 表的索引 `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 表的索引 `lesson`
--
ALTER TABLE `lesson`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `log_history`
--
ALTER TABLE `log_history`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `selectable_lesson`
--
ALTER TABLE `selectable_lesson`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `test_history`
--
ALTER TABLE `test_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `belong_to` (`belong_to`);

--
-- 表的索引 `tutor`
--
ALTER TABLE `tutor`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `admin`
--
ALTER TABLE `admin`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `log_history`
--
ALTER TABLE `log_history`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `selectable_lesson`
--
ALTER TABLE `selectable_lesson`
  MODIFY `id` bigint(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- 使用表AUTO_INCREMENT `test_history`
--
ALTER TABLE `test_history`
  MODIFY `id` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
