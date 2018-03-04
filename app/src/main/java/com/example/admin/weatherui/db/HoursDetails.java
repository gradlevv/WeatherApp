package com.example.admin.weatherui.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.NotNull;


@Entity(active = true)
public class HoursDetails {

    @Id(autoincrement = true)
    private Long id;
    private String wind_cdir;
    private double rh;
    private double wind_spd;
    private double pop;
    private String wind_cdir_full;
    private double app_temp;
    private double pres;
    private double dewpt;
    private double snow;
    private double uv;
    private double wind_dir;
    private String pod;
    private double dhi;
    private int ts;
    private double snow_depth;
    private double precip;
    private String datetime;
    private double temp;
    private double slp;
    private double clouds;
    private double vis;
    private double snow6h;
    private double precip6h;
    private long hourId;
    private long weatherIconsId;

    @ToOne(joinProperty = "weatherIconsId")
    private WeatherIcons weatherIcons;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1052925918)
    private transient HoursDetailsDao myDao;

    @Generated(hash = 1190297122)
    public HoursDetails(Long id, String wind_cdir, double rh, double wind_spd,
            double pop, String wind_cdir_full, double app_temp, double pres,
            double dewpt, double snow, double uv, double wind_dir, String pod,
            double dhi, int ts, double snow_depth, double precip, String datetime,
            double temp, double slp, double clouds, double vis, double snow6h,
            double precip6h, long hourId, long weatherIconsId) {
        this.id = id;
        this.wind_cdir = wind_cdir;
        this.rh = rh;
        this.wind_spd = wind_spd;
        this.pop = pop;
        this.wind_cdir_full = wind_cdir_full;
        this.app_temp = app_temp;
        this.pres = pres;
        this.dewpt = dewpt;
        this.snow = snow;
        this.uv = uv;
        this.wind_dir = wind_dir;
        this.pod = pod;
        this.dhi = dhi;
        this.ts = ts;
        this.snow_depth = snow_depth;
        this.precip = precip;
        this.datetime = datetime;
        this.temp = temp;
        this.slp = slp;
        this.clouds = clouds;
        this.vis = vis;
        this.snow6h = snow6h;
        this.precip6h = precip6h;
        this.hourId = hourId;
        this.weatherIconsId = weatherIconsId;
    }

    @Generated(hash = 1725809426)
    public HoursDetails() {
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

    public double getApp_temp() {
        return this.app_temp;
    }

    public void setApp_temp(double app_temp) {
        this.app_temp = app_temp;
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

    public double getWind_dir() {
        return this.wind_dir;
    }

    public void setWind_dir(double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getPod() {
        return this.pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public double getDhi() {
        return this.dhi;
    }

    public void setDhi(double dhi) {
        this.dhi = dhi;
    }

    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
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

    public double getSlp() {
        return this.slp;
    }

    public void setSlp(double slp) {
        this.slp = slp;
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

    public double getSnow6h() {
        return this.snow6h;
    }

    public void setSnow6h(double snow6h) {
        this.snow6h = snow6h;
    }

    public double getPrecip6h() {
        return this.precip6h;
    }

    public void setPrecip6h(double precip6h) {
        this.precip6h = precip6h;
    }

    public long getHourId() {
        return this.hourId;
    }

    public void setHourId(long hourId) {
        this.hourId = hourId;
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
    @Generated(hash = 565255707)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHoursDetailsDao() : null;
    }


}
