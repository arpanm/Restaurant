import pincodeMaster from 'app/entities/pincode-master/pincode-master.reducer';
import location from 'app/entities/location/location.reducer';
import applicationUser from 'app/entities/application-user/application-user.reducer';
import otp from 'app/entities/otp/otp.reducer';
import restaurant from 'app/entities/restaurant/restaurant.reducer';
import banner from 'app/entities/banner/banner.reducer';
import foodCategory from 'app/entities/food-category/food-category.reducer';
import price from 'app/entities/price/price.reducer';
import qtyUnit from 'app/entities/qty-unit/qty-unit.reducer';
import quantity from 'app/entities/quantity/quantity.reducer';
import nutrition from 'app/entities/nutrition/nutrition.reducer';
import ingredienceMaster from 'app/entities/ingredience-master/ingredience-master.reducer';
import item from 'app/entities/item/item.reducer';
import weightInfo from 'app/entities/weight-info/weight-info.reducer';
import calorieInfo from 'app/entities/calorie-info/calorie-info.reducer';
import mealPlanSettings from 'app/entities/meal-plan-settings/meal-plan-settings.reducer';
import avoid from 'app/entities/avoid/avoid.reducer';
import mealPlan from 'app/entities/meal-plan/meal-plan.reducer';
import mealPlanItem from 'app/entities/meal-plan-item/meal-plan-item.reducer';
import skipDate from 'app/entities/skip-date/skip-date.reducer';
import discount from 'app/entities/discount/discount.reducer';
import cartItem from 'app/entities/cart-item/cart-item.reducer';
import cart from 'app/entities/cart/cart.reducer';
import orderItem from 'app/entities/order-item/order-item.reducer';
import coupon from 'app/entities/coupon/coupon.reducer';
import order from 'app/entities/order/order.reducer';
import payment from 'app/entities/payment/payment.reducer';
import refund from 'app/entities/refund/refund.reducer';
import itemPincodeMapping from 'app/entities/item-pincode-mapping/item-pincode-mapping.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  pincodeMaster,
  location,
  applicationUser,
  otp,
  restaurant,
  banner,
  foodCategory,
  price,
  qtyUnit,
  quantity,
  nutrition,
  ingredienceMaster,
  item,
  weightInfo,
  calorieInfo,
  mealPlanSettings,
  avoid,
  mealPlan,
  mealPlanItem,
  skipDate,
  discount,
  cartItem,
  cart,
  orderItem,
  coupon,
  order,
  payment,
  refund,
  itemPincodeMapping,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
