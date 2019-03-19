import { TestBed, inject } from '@angular/core/testing';

import { PendingInvoiceService } from './pending-invoice.service';

describe('PendingInvoiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PendingInvoiceService]
    });
  });

  it('should be created', inject([PendingInvoiceService], (service: PendingInvoiceService) => {
    expect(service).toBeTruthy();
  }));
});
