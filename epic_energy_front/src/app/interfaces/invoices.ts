export interface iInvoices {
  date: Date;
  amount: number;
  invoiceNumber: string;
  status: 'PENDING' | 'PAID' | 'CANCELLED';
  clientId: number;
}
