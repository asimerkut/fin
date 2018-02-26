import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MasrafTipi } from './masraf-tipi.model';
import { MasrafTipiService } from './masraf-tipi.service';

@Injectable()
export class MasrafTipiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private masrafTipiService: MasrafTipiService

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
                this.masrafTipiService.find(id)
                    .subscribe((masrafTipiResponse: HttpResponse<MasrafTipi>) => {
                        const masrafTipi: MasrafTipi = masrafTipiResponse.body;
                        this.ngbModalRef = this.masrafTipiModalRef(component, masrafTipi);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.masrafTipiModalRef(component, new MasrafTipi());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    masrafTipiModalRef(component: Component, masrafTipi: MasrafTipi): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.masrafTipi = masrafTipi;
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
