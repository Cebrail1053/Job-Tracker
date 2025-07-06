-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema jobtrackerdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `jobtrackerdb` ;

-- -----------------------------------------------------
-- Schema jobtrackerdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jobtrackerdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `jobtrackerdb` ;

-- -----------------------------------------------------
-- Table `jobtrackerdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jobtrackerdb`.`user` (
  `id` BINARY(16) NOT NULL,
  `first_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(75) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('USER', 'ADMIN') NOT NULL,
  `locked` TINYINT NOT NULL,
  `enabled` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `jobtrackerdb`.`application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jobtrackerdb`.`application` (
  `job_id` INT NOT NULL AUTO_INCREMENT,
  `job_title` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NOT NULL,
  `date_applied` DATETIME NULL DEFAULT NULL,
  `location` VARCHAR(60) NULL DEFAULT NULL,
  `portal_url` VARCHAR(2048) NULL DEFAULT NULL,
  `status` TINYINT NOT NULL,
  `notes` MEDIUMTEXT NULL DEFAULT NULL,
  `user_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`job_id`, `user_id`),
  UNIQUE INDEX `job_id_UNIQUE` (`job_id` ASC) VISIBLE,
  INDEX `fk_application_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_application_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `jobtrackerdb`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
