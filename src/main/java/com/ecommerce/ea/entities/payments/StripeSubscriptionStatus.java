package com.ecommerce.ea.entities.payments;

public enum StripeSubscriptionStatus {
    TRIALING,
    ACTIVE,
    PAST_DUE,
    CANCELED,
    INCOMPLETE,
    INCOMPLETE_EXPIRED,
    UNPAID;

    public static StripeSubscriptionStatus fromStripe(String stripeStatus) {
        try {
            return StripeSubscriptionStatus.valueOf(stripeStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown Stripe status: " + stripeStatus);
        }
    }
}
