npm install jquery -S
npm install jquery-ui -S
npm install -D @types/jquery
npm install pivottable

jhipster import-jdl hope-jdl.jh
npm install -g generator-jhipster-entity-audit
yo jhipster-entity-audit default

global.css eklemesi

package.json ->
  "dependencies": {
        "jquery": "^1.12.1",
        "jquery-ui": "^1.12.1",
        "jquery-ui-dist": "^1.12.1",
        "pivottable": "^2.18.0",
  }



alter table dosya_tipi DROP COLUMN kod;
alter table borc_tipi DROP COLUMN kod;
alter table masraf_tipi DROP COLUMN kod;
alter table borc_grubu DROP COLUMN kod;
alter table borc_kalem DROP COLUMN kod;
alter table islem_kodu DROP COLUMN kod;
alter table dosya DROP COLUMN kod;
alter table borc DROP COLUMN kod;
alter table masraf DROP COLUMN kod;
alter table dosya_borc DROP COLUMN kod;
alter table dosya_borc_kalem DROP COLUMN kod;
alter table finansal_hareket DROP COLUMN kod;
alter table finansal_hareket_detay DROP COLUMN kod;

alter table dosya_tipi add kod as ('DT:'||to_char(id));
alter table borc_tipi add kod as ('BT:'||to_char(id));
alter table masraf_tipi add kod as ('MT:'||to_char(id));
alter table borc_grubu add kod as ('BG:'||to_char(id));
alter table borc_kalem add kod as ('BK:'||to_char(id));
alter table islem_kodu add kod as ('IK:'||to_char(id));
alter table dosya add kod as ('DO:'||to_char(id));
alter table borc add kod as ('BO:'||to_char(id));
alter table masraf add kod as ('MA:'||to_char(id));
alter table dosya_borc add kod as ('DB:'||to_char(id));
alter table dosya_borc_kalem add kod as ('DBK:'||to_char(id));
alter table finansal_hareket add kod as ('FH:'||to_char(id));
alter table finansal_hareket_detay add kod as ('FHD:'||to_char(id));
