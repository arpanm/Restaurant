export interface IPincodeMaster {
  id?: number;
  pincode?: string | null;
  area?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  country?: string | null;
}

export const defaultValue: Readonly<IPincodeMaster> = {};
