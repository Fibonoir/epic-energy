export interface Address {
  street: string;
  houseNumber: string;
  locality: string;
  postalCode: string;
  provinceCode: string;
}

export interface Company {
  id: number;
  companyName: string;
  vatNumber: string;
  companyEmail: string;
  pecEmail: string;
  companyType: string;
  contactEmail: string;
  contactFirstName: string;
  contactLastName: string;
  contactPhone: string;
  contactMobile: string;
  insertionDate: string;
  lastContactDate: string;
  annualTurnover: number;
  companyLogo: string;
  legalAddress: Address;
  operationalAddress: Address;
}

export interface Sort {
  empty: boolean;
  unsorted: boolean;
  sorted: boolean;
}

export interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  unpaged: boolean;
  paged: boolean;
}

export interface ResponseBody {
  content: Company[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

export interface AllClientsResponse {
  content: Company[];
  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}
