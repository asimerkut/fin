import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FinansalHareketDetay } from './finansal-hareket-detay.model';
import { FinansalHareketDetayService } from './finansal-hareket-detay.service';

@Injectable()
export class FinansalHareketDetayPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private finansalHareketDetayService: FinansalHareketDetayService

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
                this.finansalHareketDetayService.find(id)
                    .subscribe((finansalHareketDetayResponse: HttpResponse<FinansalHareketDetay>) => {
                        const finansalHareketDetay: FinansalHareketDetay = finansalHareketDetayResponse.body;
                        this.ngbModalRef = this.finansalHareketDetayModalRef(component, finansalHareketDetay);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.finansalHareketDetayModalRef(component, new FinansalHareketDetay());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    finansalHareketDetayModalRef(component: Component, finansalHareketDetay: FinansalHareketDetay): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.finansalHareketDetay = finansalHareketDetay;
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
