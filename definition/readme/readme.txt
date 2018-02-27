npm install jquery -S
npm install jquery-ui -S
npm install -D @types/jquery
npm install pivottable

jhipster import-jdl jdl-pivot.jh
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


SELECT t1.hesap, t1.karsi_hesap, t1.hesap_yonu*t1.tutar as tutar,
             t2.kod as fin_har_kod,
             t3.kod as dosya_kod,
             t4.borc_grubu as borc_grub,
             t5.borc_kalem, t5.kod as kalem_kod,
             t6.kod as borc_kod, t7.kod masraf_kod,
             substr(t1.hesap,1,1) Ana_hesap,
             (case when t1.hesap_yonu=+1 then '(+)' when t1.hesap_yonu=-1 then '(-)' else '' end) as hesap_yonu
FROM finansal_hareket_detay t1
            INNER JOIN finansal_hareket t2 ON t2.id = t1.finansal_hareket_id
            INNER JOIN dosya t3 ON t3.id = t2.dosya_id
            INNER JOIN dosya_borc t4 ON  t4.id = t1.dosya_borc_id
            LEFT OUTER JOIN dosya_borc_kalem t5 ON  t5.id = t1.dosya_borc_kalem_id
            LEFT OUTER JOIN borc t6 ON t6.id =  t5.borc_id
            LEFT OUTER JOIN masraf t7 ON t7.id =  t5.masraf_id
 where t1.hesap_yonu != 0

update def_pivot
set pvt_val='TUTAR',  pvt_col='ANA_HESAP,HESAP_YONU', pvt_row='BORC_GRUB'
WHERE id=1051;

update def_pivot
set pvt_val='ID'
WHERE id=1151;

alter table def_pivot add pvt_val varchar2(255);
alter table def_pivot add pvt_col varchar2(255);
alter table def_pivot add pvt_row varchar2(255);
