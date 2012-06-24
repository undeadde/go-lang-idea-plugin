package ro.redeul.google.go.lang.psi.impl.types;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import ro.redeul.google.go.lang.psi.GoPsiElement;
import ro.redeul.google.go.lang.psi.impl.GoPsiPackagedElementBase;
import ro.redeul.google.go.lang.psi.types.GoType;
import ro.redeul.google.go.lang.psi.types.GoTypeSlice;
import ro.redeul.google.go.lang.psi.types.underlying.GoUnderlyingTypeSlice;
import ro.redeul.google.go.lang.psi.visitors.GoElementVisitor;
import ro.redeul.google.go.lang.psi.types.underlying.GoUnderlyingType;

/**
 * Author: Toader Mihai Claudiu <mtoader@gmail.com>
 * <p/>
 * Date: Sep 2, 2010
 * Time: 12:50:44 PM
 */
public class GoTypeSliceImpl extends GoPsiPackagedElementBase implements GoTypeSlice {
    public GoTypeSliceImpl(@NotNull ASTNode node) {
        super(node);
    }

    public GoType getElementType() {
        return findChildByClass(GoType.class);
    }

    @Override
    public void accept(GoElementVisitor visitor) {
        visitor.visitSliceType(this);
    }

    @Override
    public GoPsiElement[] getMembers() {
        return new GoPsiElement[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GoType getMemberType(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GoUnderlyingType getUnderlyingType() {
        return new GoUnderlyingTypeSlice(getElementType().getUnderlyingType());
    }

    @Override
    public boolean isIdentical(GoType goType) {
        if (!(goType instanceof GoTypeSlice))
            return false;

        GoTypeSlice otherTypeSlice = (GoTypeSlice)goType;

        return getElementType().isIdentical(otherTypeSlice.getElementType());
    }
}
