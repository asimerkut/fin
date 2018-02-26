import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { DosyaBorcKalemService } from './dosya-borc-kalem.service';

@Injectable()
export class DosyaBorcKalemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dosyaBorcKalemService: DosyaBorcKalemService

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
                this.dosyaBorcKalemService.find(id)
                    .subscribe((dosyaBorcKalemResponse: HttpResponse<DosyaBorcKalem>) => {
                        const dosyaBorcKalem: DosyaBorcKalem = dosyaBorcKalemResponse.body;
                        this.ngbModalRef = this.dosyaBorcKalemModalRef(component, dosyaBorcKalem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dosyaBorcKalemModalRef(component, new DosyaBorcKalem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dosyaBorcKalemModalRef(component: Component, dosyaBorcKalem: DosyaBorcKalem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dosyaBorcKalem = dosyaBorcKalem;
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
