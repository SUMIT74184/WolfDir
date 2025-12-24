"use client"

import * as React from "react"
import { ThemeProvider as NextThemesProvider } from "next-themes"

export function ThemeProvider({ children, ...props }) {
    const [mounted, setMounted] = React.useState(false)

    React.useEffect(() => {
        setMounted(true)
    }, [])

    // Keep provider mounted but don't render children until client to avoid hydration issues.
    return (
        <NextThemesProvider
            attribute="class"
            defaultTheme="dark"
            enableSystem
            enableColorScheme
            storageKey="blogify-theme"
            {...props}
        >
            {mounted ? children : null}
        </NextThemesProvider>
    )
}