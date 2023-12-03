import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import QtyUnit from './qty-unit';
import QtyUnitDetail from './qty-unit-detail';
import QtyUnitUpdate from './qty-unit-update';
import QtyUnitDeleteDialog from './qty-unit-delete-dialog';

const QtyUnitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<QtyUnit />} />
    <Route path="new" element={<QtyUnitUpdate />} />
    <Route path=":id">
      <Route index element={<QtyUnitDetail />} />
      <Route path="edit" element={<QtyUnitUpdate />} />
      <Route path="delete" element={<QtyUnitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QtyUnitRoutes;
