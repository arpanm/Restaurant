export interface IBanner {
  id?: number;
  title?: string | null;
  imageUrl?: string | null;
  bannerLink?: string | null;
  description?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  isActive?: boolean | null;
}

export const defaultValue: Readonly<IBanner> = {
  isActive: false,
};
