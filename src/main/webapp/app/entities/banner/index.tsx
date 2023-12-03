import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Banner from './banner';
import BannerDetail from './banner-detail';
import BannerUpdate from './banner-update';
import BannerDeleteDialog from './banner-delete-dialog';

const BannerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Banner />} />
    <Route path="new" element={<BannerUpdate />} />
    <Route path=":id">
      <Route index element={<BannerDetail />} />
      <Route path="edit" element={<BannerUpdate />} />
      <Route path="delete" element={<BannerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BannerRoutes;
