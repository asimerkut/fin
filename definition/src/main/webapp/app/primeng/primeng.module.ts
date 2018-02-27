
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinButtonDemoModule } from './buttons/button/buttondemo.module';
import { FinSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { FinDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { FinConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { FinLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { FinTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { FinOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { FinSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { FinKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { FinInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { FinInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { FinInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { FinCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { FinCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { FinChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { FinColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { FinInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { FinInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { FinPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { FinAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { FinSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { FinSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { FinRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { FinSelectDemoModule } from './inputs/select/selectdemo.module';
import { FinSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { FinListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { FinRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { FinToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { FinEditorDemoModule } from './inputs/editor/editordemo.module';

import { FinGrowlDemoModule } from './messages/growl/growldemo.module';
import { FinMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { FinGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { FinFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { FinAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { FinPanelDemoModule } from './panel/panel/paneldemo.module';
import { FinTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { FinFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { FinToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { FinGridDemoModule } from './panel/grid/griddemo.module';
import { FinScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { FinCardDemoModule } from './panel/card/carddemo.module';

import { FinDataTableDemoModule } from './data/datatable/datatabledemo.module';
import { FinTableDemoModule } from './data/table/tabledemo.module';
import { FinDataGridDemoModule } from './data/datagrid/datagriddemo.module';
import { FinDataListDemoModule } from './data/datalist/datalistdemo.module';
import { FinDataScrollerDemoModule } from './data/datascroller/datascrollerdemo.module';
import { FinPickListDemoModule } from './data/picklist/picklistdemo.module';
import { FinOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { FinScheduleDemoModule } from './data/schedule/scheduledemo.module';
import { FinTreeDemoModule } from './data/tree/treedemo.module';
import { FinTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { FinPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { FinGmapDemoModule } from './data/gmap/gmapdemo.module';
import { FinOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { FinCarouselDemoModule } from './data/carousel/carouseldemo.module';

import { FinBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { FinDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { FinLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { FinPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { FinPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { FinRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { FinDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { FinMenuDemoModule } from './menu/menu/menudemo.module';
import { FinContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { FinPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { FinStepsDemoModule } from './menu/steps/stepsdemo.module';
import { FinTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { FinBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { FinMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { FinMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { FinSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { FinTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { FinBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { FinCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { FinDeferDemoModule } from './misc/defer/deferdemo.module';
import { FinInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { FinProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { FinRTLDemoModule } from './misc/rtl/rtldemo.module';
import { FinTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { FinValidationDemoModule } from './misc/validation/validationdemo.module';
import { FinProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [

        FinMenuDemoModule,
        FinContextMenuDemoModule,
        FinPanelMenuDemoModule,
        FinStepsDemoModule,
        FinTieredMenuDemoModule,
        FinBreadcrumbDemoModule,
        FinMegaMenuDemoModule,
        FinMenuBarDemoModule,
        FinSlideMenuDemoModule,
        FinTabMenuDemoModule,

        FinBlockUIDemoModule,
        FinCaptchaDemoModule,
        FinDeferDemoModule,
        FinInplaceDemoModule,
        FinProgressBarDemoModule,
        FinInputMaskDemoModule,
        FinRTLDemoModule,
        FinTerminalDemoModule,
        FinValidationDemoModule,

        FinButtonDemoModule,
        FinSplitbuttonDemoModule,

        FinInputTextDemoModule,
        FinInputTextAreaDemoModule,
        FinInputGroupDemoModule,
        FinCalendarDemoModule,
        FinChipsDemoModule,
        FinInputMaskDemoModule,
        FinInputSwitchDemoModule,
        FinPasswordIndicatorDemoModule,
        FinAutoCompleteDemoModule,
        FinSliderDemoModule,
        FinSpinnerDemoModule,
        FinRatingDemoModule,
        FinSelectDemoModule,
        FinSelectButtonDemoModule,
        FinListboxDemoModule,
        FinRadioButtonDemoModule,
        FinToggleButtonDemoModule,
        FinEditorDemoModule,
        FinColorPickerDemoModule,
        FinCheckboxDemoModule,
        FinKeyFilterDemoModule,

        FinGrowlDemoModule,
        FinMessagesDemoModule,
        FinGalleriaDemoModule,

        FinFileUploadDemoModule,

        FinAccordionDemoModule,
        FinPanelDemoModule,
        FinTabViewDemoModule,
        FinFieldsetDemoModule,
        FinToolbarDemoModule,
        FinGridDemoModule,
        FinScrollPanelDemoModule,
        FinCardDemoModule,

        FinBarchartDemoModule,
        FinDoughnutchartDemoModule,
        FinLinechartDemoModule,
        FinPiechartDemoModule,
        FinPolarareachartDemoModule,
        FinRadarchartDemoModule,

        FinDragDropDemoModule,

        FinDialogDemoModule,
        FinConfirmDialogDemoModule,
        FinLightboxDemoModule,
        FinTooltipDemoModule,
        FinOverlayPanelDemoModule,
        FinSideBarDemoModule,

        FinDataTableDemoModule,
        FinTableDemoModule,
        FinDataGridDemoModule,
        FinDataListDemoModule,
        FinDataScrollerDemoModule,
        FinScheduleDemoModule,
        FinOrderListDemoModule,
        FinPickListDemoModule,
        FinTreeDemoModule,
        FinTreeTableDemoModule,
        FinPaginatorDemoModule,
        FinOrgChartDemoModule,
        FinGmapDemoModule,
        FinCarouselDemoModule,
        FinProgressSpinnerDemoModule

    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinprimengModule {}
