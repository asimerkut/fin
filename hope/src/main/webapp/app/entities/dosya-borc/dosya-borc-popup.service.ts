import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DosyaBorc } from './dosya-borc.model';
import { DosyaBorcService } from './dosya-borc.service';

@Injectable()
export class DosyaBorcPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dosyaBorcService: DosyaBorcService

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
                this.dosyaBorcService.find(id)
                    .subscribe((dosyaBorcResponse: HttpResponse<DosyaBorc>) => {
                        const dosyaBorc: DosyaBorc = dosyaBorcResponse.body;
                        this.ngbModalRef = this.dosyaBorcModalRef(component, dosyaBorc);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dosyaBorcModalRef(component, new DosyaBorc());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dosyaBorcModalRef(component: Component, dosyaBorc: DosyaBorc): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dosyaBorc = dosyaBorc;
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
