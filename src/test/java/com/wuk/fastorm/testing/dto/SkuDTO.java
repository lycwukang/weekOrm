package com.wuk.fastorm.testing.dto;

import com.wuk.fastorm.annontation.FastormColumn;
import com.wuk.fastorm.annontation.FastormTable;

import java.math.BigDecimal;
import java.util.Date;

@FastormTable("scm_sku")
public class SkuDTO {

    @FastormColumn(value = "id", autoIncrement = true)
    private Long id;
    @FastormColumn(value = "tenant_id")
    private Long tenantId;
    @FastormColumn(value = "create_date")
    private Date createDate;
    @FastormColumn(value = "modify_date")
    private Date modifyDate;
    @FastormColumn(value = "type")
    private Integer type;
    @FastormColumn(value = "kind")
    private Integer kind;
    @FastormColumn(value = "supplier_id")
    private Long supplierId;
    @FastormColumn(value = "goods_id")
    private Long goodsId;
    @FastormColumn(value = "sn")
    private String sn;
    @FastormColumn(value = "name")
    private String name;
    @FastormColumn(value = "desc")
    private String desc;
    @FastormColumn(value = "barcode")
    private String barcode;
    @FastormColumn(value = "image")
    private String image;
    @FastormColumn(value = "link")
    private String link;
    @FastormColumn(value = "tb_link")
    private String tbLink;
    @FastormColumn(value = "jd_link")
    private String jdLink;
    @FastormColumn(value = "origin")
    private String origin;
    @FastormColumn(value = "brand_id")
    private Long brandId;
    @FastormColumn(value = "category_id")
    private Long categoryId;
    @FastormColumn(value = "volume")
    private Float volume;
    @FastormColumn(value = "length")
    private Float length;
    @FastormColumn(value = "width")
    private Float width;
    @FastormColumn(value = "height")
    private Float height;
    @FastormColumn(value = "weight")
    private Float weight;
    @FastormColumn(value = "state")
    private String state;
    @FastormColumn(value = "city")
    private String city;
    @FastormColumn(value = "overseas")
    private Boolean overseas;
    @FastormColumn(value = "free_postage")
    private Boolean freePostage;
    @FastormColumn(value = "market_price")
    private BigDecimal marketPrice;
    @FastormColumn(value = "sale_price")
    private BigDecimal salePrice;
    @FastormColumn(value = "supplier_price")
    private BigDecimal supplierPrice;
    @FastormColumn(value = "group_price")
    private BigDecimal groupPrice;
    @FastormColumn(value = "stock")
    private Integer stock;
    @FastormColumn(value = "warning_stock")
    private Integer warningStock;
    @FastormColumn(value = "zone_temp_id")
    private Long zoneTempId;
    @FastormColumn(value = "outer_id")
    private String outerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTbLink() {
        return tbLink;
    }

    public void setTbLink(String tbLink) {
        this.tbLink = tbLink;
    }

    public String getJdLink() {
        return jdLink;
    }

    public void setJdLink(String jdLink) {
        this.jdLink = jdLink;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getOverseas() {
        return overseas;
    }

    public void setOverseas(Boolean overseas) {
        this.overseas = overseas;
    }

    public Boolean getFreePostage() {
        return freePostage;
    }

    public void setFreePostage(Boolean freePostage) {
        this.freePostage = freePostage;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(BigDecimal supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    public BigDecimal getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getWarningStock() {
        return warningStock;
    }

    public void setWarningStock(Integer warningStock) {
        this.warningStock = warningStock;
    }

    public Long getZoneTempId() {
        return zoneTempId;
    }

    public void setZoneTempId(Long zoneTempId) {
        this.zoneTempId = zoneTempId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }
}
