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
CREATE SCHEMA IF NOT EXISTS `jobtrackerdb` ;
USE `jobtrackerdb` ;

-- -----------------------------------------------------
-- Table `jobtrackerdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jobtrackerdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(75) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jobtrackerdb`.`application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jobtrackerdb`.`application` (
  `job_id` INT NOT NULL,
  `job_title` VARCHAR(100) NOT NULL,
  `company` VARCHAR(100) NOT NULL,
  `date_applied` DATETIME NULL,
  `location` VARCHAR(60) NULL,
  `portal_url` VARCHAR(2048) NULL,
  `status` TINYINT(3) NOT NULL,
  `notes` MEDIUMTEXT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`job_id`, `user_id`),
  UNIQUE INDEX `job_id_UNIQUE` (`job_id` ASC) VISIBLE,
  INDEX `fk_application_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_application_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `jobtrackerdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
