package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pointResults")
@NamedQueries({
        @NamedQuery(
                name = "PointResult.findAll",
                query = "SELECT p FROM PointResult p ORDER BY p.timestamp DESC"
        )
})
public class PointResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x", nullable = false)
    private BigDecimal x;

    @Column(name = "y", nullable = false)
    private BigDecimal y;

    @Column(name = "r", nullable = false)
    private BigDecimal r;

    @Column(name = "hit", nullable = false)
    private Boolean hit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "execTime", nullable = false)
    private Long execTime;

    public PointResult() {
    }

    public PointResult(BigDecimal x, BigDecimal y, BigDecimal r, Boolean hit, Date timestamp, Long execTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.timestamp = timestamp;
        this.execTime = execTime;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }
    
    public BigDecimal getY() {
        return y;
    }
    
    public void setY(BigDecimal y) {
        this.y = y;
    }
    
    public BigDecimal getR() {
        return r;
    }
    
    public void setR(BigDecimal r) {
        this.r = r;
    }
    
    public Boolean getHit() {
        return hit;
    }
    
    public void setHit(Boolean hit) {
        this.hit = hit;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public Long getExecTime() {
        return execTime;
    }
    
    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }
}
