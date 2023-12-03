import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import IngredienceMaster from './ingredience-master';
import IngredienceMasterDetail from './ingredience-master-detail';
import IngredienceMasterUpdate from './ingredience-master-update';
import IngredienceMasterDeleteDialog from './ingredience-master-delete-dialog';

const IngredienceMasterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<IngredienceMaster />} />
    <Route path="new" element={<IngredienceMasterUpdate />} />
    <Route path=":id">
      <Route index element={<IngredienceMasterDetail />} />
      <Route path="edit" element={<IngredienceMasterUpdate />} />
      <Route path="delete" element={<IngredienceMasterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IngredienceMasterRoutes;
