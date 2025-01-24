export interface iInvoices {
  id: number;
  date: Date;
  amount: number;
  invoiceNumber: string;
  status: 'PENDING' | 'PAID' | 'CANCELLED';
  clientId: number;
}
