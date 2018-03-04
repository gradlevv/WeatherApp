package com.example.admin.weatherui.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;


@Entity(active = true)
public class Hour {

    @Id(autoincrement = true)
    private Long id;
    private String city_name;
    private double lon;
    private String timezone;
    private double lat;
    private String country_code;
    private String state_code;


    @ToMany(referencedJoinProperty = "hourId")
    private List<HoursDetails> data=new ArrayList<>();
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 527896662)
    private transient HourDao myDao;


    @Generated(hash = 323876184)
    public Hour(Long id, String city_name, double lon, String timezone, double lat,
            String country_code, String state_code) {
        this.id = id;
        this.city_name = city_name;
        this.lon = lon;
        this.timezone = timezone;
        this.lat = lat;
        this.country_code = country_code;
        this.state_code = state_code;
    }


    @Generated(hash = 18112628)
    public Hour() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getCity_name() {
        return this.city_name;
    }


    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    public double getLon() {
        return this.lon;
    }


    public void setLon(double lon) {
        this.lon = lon;
    }


    public String getTimezone() {
        return this.timezone;
    }


    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }


    public double getLat() {
        return this.lat;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }


    public String getCountry_code() {
        return this.country_code;
    }


    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }


    public String getState_code() {
        return this.state_code;
    }


    public void setState_code(String state_code) {
        this.state_code = state_code;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 347679119)
    public List<HoursDetails> getData() {
        if (data == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HoursDetailsDao targetDao = daoSession.getHoursDetailsDao();
            List<HoursDetails> dataNew = targetDao._queryHour_Data(id);
            synchronized (this) {
                if (data == null) {
                    data = dataNew;
                }
            }
        }
        return data;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1283600904)
    public synchronized void resetData() {
        data = null;
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
    @Generated(hash = 442919514)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHourDao() : null;
    }

}
