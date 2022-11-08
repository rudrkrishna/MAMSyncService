package com.example.MAMSyncService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assets")
@Data
public class Assets {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "parentid")
    private Integer parentId;

    @Column(name = "type")//
    private String type;

    @Column(name = "filename")//
    private String filename;

    @Column(name = "path")//
    private String path;

    @Column(name = "mimetype")//
    private String mimetype;

    @Column(name = "creationdate")
    private Integer creationDate;

    @Column(name = "modificationdate")
    private Integer modificationDate;

    @Column(name = "userowner")
    private Integer userOwner;

    @Column(name = "customsettings")
    private String customSettings;

    @Column(name = "usermodification")
    private Integer userModification;

    @Column(name = "hasmetadata")
    private Byte hasMetaData;

    @Column(name = "versioncount")
    private Integer versionCount;
}
