import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { HopFinansalHareketDetay } from './hop-finansal-hareket-detay.model';
import { HopFinansalHareketDetayService } from './hop-finansal-hareket-detay.service';

@Injectable()
export class HopFinansalHareketDetayPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private hopFinansalHareketDetayService: HopFinansalHareketDetayService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.hopFinansalHareketDetayService.find(id)
                    .subscribe((hopFinansalHareketDetayResponse: HttpResponse<HopFinansalHareketDetay>) => {
                        const hopFinansalHareketDetay: HopFinansalHareketDetay = hopFinansalHareketDetayResponse.body;
                        this.ngbModalRef = this.hopFinansalHareketDetayModalRef(component, hopFinansalHareketDetay);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hopFinansalHareketDetayModalRef(component, new HopFinansalHareketDetay());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hopFinansalHareketDetayModalRef(component: Component, hopFinansalHareketDetay: HopFinansalHareketDetay): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hopFinansalHareketDetay = hopFinansalHareketDetay;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
