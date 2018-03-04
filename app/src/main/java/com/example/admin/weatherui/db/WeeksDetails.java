package com.example.admin.weatherui.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.NotNull;


@Entity(active = true)
public class WeeksDetails {


    @Id(autoincrement = true)
    private Long id;
    private String wind_cdir;
    private double rh;
    private double wind_spd;
    private double pop;
    private String wind_cdir_full;
    private double slp;
    private double app_max_temp;
    private double pres;
    private double dewpt;
    private double snow;
    private double uv;
    private int ts;
    private double wind_dir;
    private double app_min_temp;
    private double max_temp;
    private double snow_depth;
    private double precip;
    private double max_dhi;
    private String datetime;
    private double temp;
    private double min_temp;
    private double clouds;
    private double vis;
    private long weeksId;
    private long weatherIconsId;

    @ToOne(joinProperty = "weatherIconsId")
    private WeatherIcons weatherIcons;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1872616952)
    private transient WeeksDetailsDao myDao;

    @Generated(hash = 356089828)
    public WeeksDetails(Long id, String wind_cdir, double rh, double wind_spd,
            double pop, String wind_cdir_full, double slp, double app_max_temp,
            double pres, double dewpt, double snow, double uv, int ts,
            double wind_dir, double app_min_temp, double max_temp,
            double snow_depth, double precip, double max_dhi, String datetime,
            double temp, double min_temp, double clouds, double vis, long weeksId,
            long weatherIconsId) {
        this.id = id;
        this.wind_cdir = wind_cdir;
        this.rh = rh;
        this.wind_spd = wind_spd;
        this.pop = pop;
        this.wind_cdir_full = wind_cdir_full;
        this.slp = slp;
        this.app_max_temp = app_max_temp;
        this.pres = pres;
        this.dewpt = dewpt;
        this.snow = snow;
        this.uv = uv;
        this.ts = ts;
        this.wind_dir = wind_dir;
        this.app_min_temp = app_min_temp;
        this.max_temp = max_temp;
        this.snow_depth = snow_depth;
        this.precip = precip;
        this.max_dhi = max_dhi;
        this.datetime = datetime;
        this.temp = temp;
        this.min_temp = min_temp;
        this.clouds = clouds;
        this.vis = vis;
        this.weeksId = weeksId;
        this.weatherIconsId = weatherIconsId;
    }

    @Generated(hash = 1434237911)
    public WeeksDetails() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWind_cdir() {
        return this.wind_cdir;
    }

    public void setWind_cdir(String wind_cdir) {
        this.wind_cdir = wind_cdir;
    }

    public double getRh() {
        return this.rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public double getWind_spd() {
        return this.wind_spd;
    }

    public void setWind_spd(double wind_spd) {
        this.wind_spd = wind_spd;
    }

    public double getPop() {
        return this.pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public String getWind_cdir_full() {
        return this.wind_cdir_full;
    }

    public void setWind_cdir_full(String wind_cdir_full) {
        this.wind_cdir_full = wind_cdir_full;
    }

    public double getSlp() {
        return this.slp;
    }

    public void setSlp(double slp) {
        this.slp = slp;
    }

    public double getApp_max_temp() {
        return this.app_max_temp;
    }

    public void setApp_max_temp(double app_max_temp) {
        this.app_max_temp = app_max_temp;
    }

    public double getPres() {
        return this.pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public double getDewpt() {
        return this.dewpt;
    }

    public void setDewpt(double dewpt) {
        this.dewpt = dewpt;
    }

    public double getSnow() {
        return this.snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getUv() {
        return this.uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public double getWind_dir() {
        return this.wind_dir;
    }

    public void setWind_dir(double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public double getApp_min_temp() {
        return this.app_min_temp;
    }

    public void setApp_min_temp(double app_min_temp) {
        this.app_min_temp = app_min_temp;
    }

    public double getMax_temp() {
        return this.max_temp;
    }

    public void setMax_temp(double max_temp) {
        this.max_temp = max_temp;
    }

    public double getSnow_depth() {
        return this.snow_depth;
    }

    public void setSnow_depth(double snow_depth) {
        this.snow_depth = snow_depth;
    }

    public double getPrecip() {
        return this.precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public double getMax_dhi() {
        return this.max_dhi;
    }

    public void setMax_dhi(double max_dhi) {
        this.max_dhi = max_dhi;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMin_temp() {
        return this.min_temp;
    }

    public void setMin_temp(double min_temp) {
        this.min_temp = min_temp;
    }

    public double getClouds() {
        return this.clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public double getVis() {
        return this.vis;
    }

    public void setVis(double vis) {
        this.vis = vis;
    }

    public long getWeeksId() {
        return this.weeksId;
    }

    public void setWeeksId(long weeksId) {
        this.weeksId = weeksId;
    }

    public long getWeatherIconsId() {
        return this.weatherIconsId;
    }

    public void setWeatherIconsId(long weatherIconsId) {
        this.weatherIconsId = weatherIconsId;
    }

    @Generated(hash = 1970771162)
    private transient Long weatherIcons__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1380627241)
    public WeatherIcons getWeatherIcons() {
        long __key = this.weatherIconsId;
        if (weatherIcons__resolvedKey == null
                || !weatherIcons__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WeatherIconsDao targetDao = daoSession.getWeatherIconsDao();
            WeatherIcons weatherIconsNew = targetDao.load(__key);
            synchronized (this) {
                weatherIcons = weatherIconsNew;
                weatherIcons__resolvedKey = __key;
            }
        }
        return weatherIcons;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 433254509)
    public void setWeatherIcons(@NotNull WeatherIcons weatherIcons) {
        if (weatherIcons == null) {
            throw new DaoException(
                    "To-one property 'weatherIconsId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.weatherIcons = weatherIcons;
            weatherIconsId = weatherIcons.getId();
            weatherIcons__resolvedKey = weatherIconsId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1311221505)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWeeksDetailsDao() : null;
    }

}
