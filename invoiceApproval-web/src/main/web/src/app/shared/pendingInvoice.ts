export class PendingInvoice{
    invoiceNumber: string;
    invoiceAmt: number;
    invoiceeEmail: string;
    invoiceImgLink: string;
    invoiceStatus: string;
    currApprovalLevel: string;
    finalApprovalLevel: string;
    vendor: string;
    createdBy: string;
    createdAt: string;
    updatedBy: string;
    updatedAt: string;
    response: any;
    invoiceDetails: any[];
  }

  export class InvoiceAction{
    username:string; // TO BE REMOVED LATER
    invoiceNumber: string;
    comments: string;
  }