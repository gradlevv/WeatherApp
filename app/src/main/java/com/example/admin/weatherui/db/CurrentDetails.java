package com.example.admin.weatherui.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.NotNull;


@Entity(active = true)
public class CurrentDetails {


    @Id(autoincrement = true)
    private Long id;
    private double rh;
    private String pod;
    private double pres;
    private String timezone;
    private String country_code;
    private double clouds;
    private double vis;
    private double wind_spd;
    private String wind_cdir_full;
    private double app_temp;
    private double lon;
    private String state_code;
    private int ts;
    private double elev_angle;
    private double h_angle;
    private double dewpt;
    private String ob_time;
    private double uv;
    private String sunset;
    private String sunrise;
    private String city_name;
    private double precip;
    private String station;
    private double lat;
    private double dhi;
    private String datetime;
    private double temp;
    private double wind_dir;
    private double slp;
    private String wind_cdir;
    private long weatherIconsId;
    private long currentsId;

    @ToOne(joinProperty = "weatherIconsId")
    private WeatherIcons weatherIcons;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 720650982)
    private transient CurrentDetailsDao myDao;

    @Generated(hash = 1589991890)
    public CurrentDetails(Long id, double rh, String pod, double pres,
            String timezone, String country_code, double clouds, double vis,
            double wind_spd, String wind_cdir_full, double app_temp, double lon,
            String state_code, int ts, double elev_angle, double h_angle,
            double dewpt, String ob_time, double uv, String sunset, String sunrise,
            String city_name, double precip, String station, double lat, double dhi,
            String datetime, double temp, double wind_dir, double slp,
            String wind_cdir, long weatherIconsId, long currentsId) {
        this.id = id;
        this.rh = rh;
        this.pod = pod;
        this.pres = pres;
        this.timezone = timezone;
        this.country_code = country_code;
        this.clouds = clouds;
        this.vis = vis;
        this.wind_spd = wind_spd;
        this.wind_cdir_full = wind_cdir_full;
        this.app_temp = app_temp;
        this.lon = lon;
        this.state_code = state_code;
        this.ts = ts;
        this.elev_angle = elev_angle;
        this.h_angle = h_angle;
        this.dewpt = dewpt;
        this.ob_time = ob_time;
        this.uv = uv;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.city_name = city_name;
        this.precip = precip;
        this.station = station;
        this.lat = lat;
        this.dhi = dhi;
        this.datetime = datetime;
        this.temp = temp;
        this.wind_dir = wind_dir;
        this.slp = slp;
        this.wind_cdir = wind_cdir;
        this.weatherIconsId = weatherIconsId;
        this.currentsId = currentsId;
    }

    @Generated(hash = 1604611798)
    public CurrentDetails() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRh() {
        return this.rh;
    }

    public void setRh(double rh) {
        this.rh = rh;
    }

    public String getPod() {
        return this.pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public double getPres() {
        return this.pres;
    }

    public void setPres(double pres) {
        this.pres = pres;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountry_code() {
        return this.country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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

    public double getWind_spd() {
        return this.wind_spd;
    }

    public void setWind_spd(double wind_spd) {
        this.wind_spd = wind_spd;
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

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getState_code() {
        return this.state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public double getElev_angle() {
        return this.elev_angle;
    }

    public void setElev_angle(double elev_angle) {
        this.elev_angle = elev_angle;
    }

    public double getH_angle() {
        return this.h_angle;
    }

    public void setH_angle(double h_angle) {
        this.h_angle = h_angle;
    }

    public double getDewpt() {
        return this.dewpt;
    }

    public void setDewpt(double dewpt) {
        this.dewpt = dewpt;
    }

    public String getOb_time() {
        return this.ob_time;
    }

    public void setOb_time(String ob_time) {
        this.ob_time = ob_time;
    }

    public double getUv() {
        return this.uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public String getSunset() {
        return this.sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return this.sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getPrecip() {
        return this.precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }

    public String getStation() {
        return this.station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getDhi() {
        return this.dhi;
    }

    public void setDhi(double dhi) {
        this.dhi = dhi;
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

    public double getWind_dir() {
        return this.wind_dir;
    }

    public void setWind_dir(double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public double getSlp() {
        return this.slp;
    }

    public void setSlp(double slp) {
        this.slp = slp;
    }

    public String getWind_cdir() {
        return this.wind_cdir;
    }

    public void setWind_cdir(String wind_cdir) {
        this.wind_cdir = wind_cdir;
    }

    public long getWeatherIconsId() {
        return this.weatherIconsId;
    }

    public void setWeatherIconsId(long weatherIconsId) {
        this.weatherIconsId = weatherIconsId;
    }

    public long getCurrentsId() {
        return this.currentsId;
    }

    public void setCurrentsId(long currentsId) {
        this.currentsId = currentsId;
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
    @Generated(hash = 743481618)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrentDetailsDao() : null;
    }


}
