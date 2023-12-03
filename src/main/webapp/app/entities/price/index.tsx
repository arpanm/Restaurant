import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Price from './price';
import PriceDetail from './price-detail';
import PriceUpdate from './price-update';
import PriceDeleteDialog from './price-delete-dialog';

const PriceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Price />} />
    <Route path="new" element={<PriceUpdate />} />
    <Route path=":id">
      <Route index element={<PriceDetail />} />
      <Route path="edit" element={<PriceUpdate />} />
      <Route path="delete" element={<PriceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PriceRoutes;
