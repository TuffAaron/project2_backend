# Database Setup Guide

## 🎯 Quick Summary

**Good News:** Your database is already fully configured and working on Heroku!
- ✅ JawsDB MySQL addon is connected
- ✅ Tables auto-create via Hibernate
- ✅ No manual setup needed

---

## For Database Administrator

### Current Heroku Production Database

**Connection Details:**
```
Host: gzp0u91edhmxszwf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com
Port: 3306
Database: na9t89qhy0p328pc
Username: xnho8lw1tvhttapm
Password: v7d9gws99qktv5yw
```

**Quick Connect (MySQL CLI):**
```bash
mysql -h gzp0u91edhmxszwf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com \
      -u xnho8lw1tvhttapm \
      -p \
      na9t89qhy0p328pc
# Password: v7d9gws99qktv5yw
```

**Tables:** Automatically created by Hibernate when app first runs
- `games` - NBA game data
- `teams` - NBA team information
- Other entities as defined in code

---

## Heroku Production Database Setup

### Option A: Using Existing JawsDB (Already Configured)

Your app is already connected to JawsDB. The credentials are in Heroku Config Vars:

```
JAWSDB_URL=jdbc:mysql://gzp0u91edhmxszwf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/na9t89qhy0p328pc
JAWSDB_USERNAME=xnho8lw1tvhttapm
JAWSDB_PASSWORD=v7d9gws99qktv5yw
```

**These are already set on Heroku and working!**

### Option B: Connect to a Different Database

If you need to connect to a different MySQL database:

#### Step 1: Set Heroku Config Vars

```bash
# Set your new database URL
heroku config:set JAWSDB_URL=jdbc:mysql://YOUR_HOST:3306/YOUR_DATABASE -a jump-ball

# Set credentials
heroku config:set JAWSDB_USERNAME=your_username -a jump-ball
heroku config:set JAWSDB_PASSWORD=your_password -a jump-ball

# Ensure database driver and dialect are set
heroku config:set DATABASE_DRIVER=com.mysql.cj.jdbc.Driver -a jump-ball
heroku config:set DATABASE_DIALECT=org.hibernate.dialect.MySQLDialect -a jump-ball
```

#### Step 2: Database Requirements

Your MySQL database needs:
- **MySQL 8.0+** (or MySQL 5.7+)
- **Character set**: UTF-8
- **Tables**: Auto-created by Hibernate (see below)

#### Step 3: Tables Created Automatically

The application uses **Hibernate DDL auto** set to `update`, which means:
- Tables are **automatically created** on first run
- Schema is **automatically updated** when models change
- No manual SQL scripts needed

Expected tables:
- `games` - NBA game data
- `teams` - NBA team information
- Other entities defined in `src/main/groovy/com/example/demo/model/`

---

## Local Development Database Setup

### Option 1: Use H2 (Default - No Setup)

The app uses H2 by default for local development:

```properties
# Already configured in .env
JAWSDB_URL=jdbc:h2:file:./data/nba_games_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
DATABASE_DRIVER=org.h2.Driver
DATABASE_DIALECT=org.hibernate.dialect.H2Dialect
```

**H2 Console Access** (when running locally):
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/nba_games_db`
- Username: `sa`
- Password: (leave blank)

### Option 2: Use Local MySQL

To use MySQL locally instead of H2:

#### Step 1: Install MySQL
- Download: https://dev.mysql.com/downloads/mysql/
- Or use Docker:
  ```bash
  docker run --name mysql-local -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=nba_games_db -p 3306:3306 -d mysql:8.0
  ```

#### Step 2: Create Database
```sql
CREATE DATABASE nba_games_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'nba_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON nba_games_db.* TO 'nba_user'@'localhost';
FLUSH PRIVILEGES;
```

#### Step 3: Update `.env` File
```properties
JAWSDB_URL=jdbc:mysql://localhost:3306/nba_games_db
DATABASE_DRIVER=com.mysql.cj.jdbc.Driver
DATABASE_DIALECT=org.hibernate.dialect.MySQLDialect
JAWSDB_USERNAME=nba_user
JAWSDB_PASSWORD=your_password
```

---

## Database Configuration Variables

### Required Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `JAWSDB_URL` | JDBC connection URL | `jdbc:mysql://host:3306/database` |
| `JAWSDB_USERNAME` | Database username | `myuser` |
| `JAWSDB_PASSWORD` | Database password | `mypassword` |
| `DATABASE_DRIVER` | JDBC driver class | `com.mysql.cj.jdbc.Driver` |
| `DATABASE_DIALECT` | Hibernate dialect | `org.hibernate.dialect.MySQLDialect` |

### Optional Configuration

| Variable | Default | Description |
|----------|---------|-------------|
| `DDL_AUTO` | `update` | Hibernate DDL mode: `update`, `create`, `create-drop`, `validate`, `none` |
| `DATA_LOAD_ENABLED` | `true` | Enable/disable initial data loading |
| `HIKARI_MAX_POOL_SIZE` | `10` | Maximum database connection pool size |
| `HIKARI_MIN_IDLE` | `2` | Minimum idle connections |

---

## Switching Between Databases

### For Local Development:
Edit your `.env` file and restart the application.

### For Heroku Production:
```bash
# View current database config
heroku config -a jump-ball | grep JAWSDB

# Update database URL
heroku config:set JAWSDB_URL=jdbc:mysql://NEW_HOST:3306/NEW_DATABASE -a jump-ball

# Restart the app
heroku restart -a jump-ball
```

---

## Troubleshooting

### Connection Refused
- **Check**: Database server is running
- **Check**: Firewall allows connection on port 3306
- **Check**: Host and port in `JAWSDB_URL` are correct

### Authentication Failed
- **Check**: Username and password are correct
- **Check**: User has privileges on the database
- **MySQL**: Run `GRANT ALL PRIVILEGES ON database.* TO 'user'@'%';`

### Tables Not Created
- **Check**: `DDL_AUTO` is set to `update` or `create`
- **Check**: Database user has CREATE TABLE privileges
- **Check**: Application logs for Hibernate errors

### Heroku Database Connection Issues
- **Check**: Config vars are set correctly: `heroku config -a jump-ball`
- **Check**: JawsDB addon is provisioned: `heroku addons -a jump-ball`
- **Check**: Logs: `heroku logs --tail -a jump-ball`

---

## Current Production Setup Summary

✅ **Already Configured and Working:**
- JawsDB MySQL addon on Heroku
- Connection credentials in Heroku Config Vars
- Automatic table creation via Hibernate
- Connection pooling optimized for production

🔧 **No database setup needed** - Everything is already configured!

---

## For Database Administrator

If you need to access the production database directly:

### Connect via MySQL Client
```bash
mysql -h gzp0u91edhmxszwf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com \
      -u xnho8lw1tvhttapm \
      -p \
      na9t89qhy0p328pc
# Password: v7d9gws99qktv5yw
```

### View Tables
```sql
USE na9t89qhy0p328pc;
SHOW TABLES;
DESCRIBE games;
DESCRIBE teams;
```

### Backup Database
```bash
mysqldump -h gzp0u91edhmxszwf.cbetxkdyhwsb.us-east-1.rds.amazonaws.com \
          -u xnho8lw1tvhttapm \
          -p \
          na9t89qhy0p328pc > backup.sql
```

---

## Need Help?

- **Check logs**: `heroku logs --tail -a jump-ball`
- **Verify config**: `heroku config -a jump-ball`
- **Test connection**: Use MySQL Workbench or DBeaver with the credentials above
